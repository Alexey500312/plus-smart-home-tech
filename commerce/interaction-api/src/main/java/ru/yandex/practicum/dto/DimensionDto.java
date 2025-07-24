package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DimensionDto {
    @NotNull(message = "Не указана ширина товара")
    @Positive(message = "Ширина товара должна быть > 0")
    private Double width;

    @NotNull(message = "Не указана высота товара")
    @Positive(message = "Высота товара должна быть > 0")
    private Double height;

    @NotNull(message = "Не указана глубина товара")
    @Positive(message = "Глубина товара должна быть > 0")
    private Double depth;
}
