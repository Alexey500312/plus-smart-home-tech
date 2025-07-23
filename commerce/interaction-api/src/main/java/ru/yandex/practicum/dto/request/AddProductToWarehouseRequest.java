package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class AddProductToWarehouseRequest {
    @NotNull(message = "Не указан идентификатор товара")
    private UUID productId;

    @NotNull(message = "Не указано количество товара")
    @Min(value = 0, message = "Количество товара не может быть меньше нуля")
    private Integer quantity;
}
