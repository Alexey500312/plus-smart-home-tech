package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ShippedToDeliveryRequest {
    @NotNull(message = "ИД заказа не может быть пустым")
    private UUID orderId;

    @NotNull(message = "ИД доставки не может быть пустым")
    private UUID deliveryId;
}
