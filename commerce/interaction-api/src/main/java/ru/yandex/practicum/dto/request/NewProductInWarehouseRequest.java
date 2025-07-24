package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.dto.DimensionDto;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class NewProductInWarehouseRequest {
    @NotNull(message = "Не указан идентификатор товара")
    private UUID productId;

    private Boolean fragile;

    private DimensionDto dimension;

    @NotNull(message = "Не указан вес товара")
    @Positive(message = "Вес товара должен быть > 0")
    private Double weight;
}
