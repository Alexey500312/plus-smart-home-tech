package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.dto.enums.DeliveryState;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class DeliveryDto {
    private UUID deliveryId;

    @NotNull(message = "Адрес склада не может быть пустым")
    private AddressDto fromAddress;

    @NotNull(message = "Адрес назначения не может быть пустым")
    private AddressDto toAddress;

    @NotNull(message = "ИД заказа не может быть пустым")
    private UUID orderId;

    private DeliveryState deliveryState;
}
