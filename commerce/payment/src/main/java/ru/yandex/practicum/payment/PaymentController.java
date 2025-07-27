package ru.yandex.practicum.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.contract.PaymentOperations;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

@RequestMapping("/api/v1/payment")
@RestController
@RequiredArgsConstructor
@Validated
public class PaymentController implements PaymentOperations {
    private final PaymentService paymentService;

    @Override
    public PaymentDto payment(OrderDto orderDto) {
        return paymentService.payment(orderDto);
    }

    @Override
    public BigDecimal calculateTotalCost(OrderDto orderDto) {
        return paymentService.calculateTotalCost(orderDto);
    }

    @Override
    public void refund(UUID paymentId) {
        paymentService.refund(paymentId);
    }

    @Override
    public BigDecimal calculateProductCost(OrderDto orderDto) {
        return paymentService.calculateProductCost(orderDto);
    }

    @Override
    public void payFailed(UUID paymentId) {
        paymentService.payFailed(paymentId);
    }
}
