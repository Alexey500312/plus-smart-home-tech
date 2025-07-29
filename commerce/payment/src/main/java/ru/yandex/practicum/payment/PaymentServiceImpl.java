package ru.yandex.practicum.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.dto.enums.PaymentState;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.feign.OrderClient;
import ru.yandex.practicum.feign.ShoppingStoreClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    private static final double FEE = 10;

    private final PaymentRepository paymentRepository;
    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;

    @Override
    @Transactional
    public PaymentDto payment(OrderDto orderDto) {
        if (orderDto.getProducts() == null || orderDto.getProducts().isEmpty() || orderDto.getDeliveryPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно информации в заказе для расчёта");
        }

        Payment payment = PaymentMapper.INSTANCE.toNewPayment(orderDto);
        payment.setDeliveryTotal(orderDto.getDeliveryPrice());
        payment.setProductTotal(calculateProductCost(orderDto));
        payment.setFeeTotal(payment.getProductTotal()
                .multiply(BigDecimal.valueOf(FEE / 100.))
                .setScale(2, RoundingMode.HALF_UP));
        payment.setTotalPayment(calculateTotalCost(orderDto));
        return PaymentMapper.INSTANCE.toPaymentDto(paymentRepository.save(payment));
    }

    @Override
    public BigDecimal calculateTotalCost(OrderDto orderDto) {
        if (orderDto.getProducts() == null || orderDto.getProducts().isEmpty() || orderDto.getDeliveryPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно информации в заказе для расчёта");
        }

        BigDecimal productCost = calculateProductCost(orderDto);
        return productCost
                .multiply(BigDecimal.valueOf(FEE / 100.))
                .setScale(2, RoundingMode.HALF_UP)
                .add(orderDto.getDeliveryPrice())
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional
    public void refund(UUID paymentId) {
        changePaymentState(paymentId, PaymentState.SUCCESS);
    }

    @Override
    public BigDecimal calculateProductCost(OrderDto orderDto) {
        if (orderDto.getProducts() == null || orderDto.getProducts().isEmpty()) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно информации в заказе для расчёта");
        }

        BigDecimal productCost = new BigDecimal(0);
        for (Map.Entry<UUID, Integer> product : orderDto.getProducts().entrySet()) {
            BigDecimal productPrice = shoppingStoreClient.findProductById(product.getKey()).getPrice();
            productCost = productCost.add(productPrice.multiply(BigDecimal.valueOf(product.getValue())));
        }
        return productCost;
    }

    @Override
    @Transactional
    public void payFailed(UUID paymentId) {
        changePaymentState(paymentId, PaymentState.FAILED);
    }

    private Payment findPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoOrderFoundException("Не найден платеж"));
    }

    private void changePaymentState(UUID paymentId, PaymentState paymentState) {
        Payment payment = findPaymentById(paymentId);
        payment.setState(paymentState);
        switch (paymentState) {
            case SUCCESS:
                orderClient.payOrder(payment.getOrderId());
                break;
            case FAILED:
                orderClient.payOrderFailed(payment.getOrderId());
                break;
        }
        paymentRepository.save(payment);
    }
}
