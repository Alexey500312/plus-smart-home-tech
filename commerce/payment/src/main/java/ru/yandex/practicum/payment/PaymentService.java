package ru.yandex.practicum.payment;

import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    PaymentDto payment(OrderDto orderDto);

    BigDecimal calculateTotalCost(OrderDto orderDto);

    void refund(UUID paymentId);

    BigDecimal calculateProductCost(OrderDto orderDto);

    void payFailed(UUID paymentId);
}
