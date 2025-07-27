package ru.yandex.practicum.delivery;

import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryService {
    DeliveryDto planDelivery(DeliveryDto deliveryDto);

    void delivery(UUID orderId);

    void shippedToDelivery(UUID orderId);

    void deliveryFailed(UUID orderId);

    BigDecimal deliveryCost(OrderDto orderDto);
}
