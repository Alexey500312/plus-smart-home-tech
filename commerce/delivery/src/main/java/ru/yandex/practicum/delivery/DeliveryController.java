package ru.yandex.practicum.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.contract.DeliveryOperations;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

@RequestMapping("/api/v1/delivery")
@RestController
@RequiredArgsConstructor
@Validated
public class DeliveryController implements DeliveryOperations {
    private final DeliveryService deliveryService;

    @Override
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        return deliveryService.planDelivery(deliveryDto);
    }

    @Override
    public void delivery(UUID orderId) {
        deliveryService.delivery(orderId);
    }

    @Override
    public void shippedToDelivery(UUID orderId) {
        deliveryService.shippedToDelivery(orderId);
    }

    @Override
    public void deliveryFailed(UUID orderId) {
        deliveryService.deliveryFailed(orderId);
    }

    @Override
    public BigDecimal deliveryCost(OrderDto orderDto) {
        return deliveryService.deliveryCost(orderDto);
    }
}
