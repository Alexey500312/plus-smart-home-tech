package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.QuantityState;
import ru.yandex.practicum.dto.enums.State;
import ru.yandex.practicum.validation.ValidatorGroups;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ProductDto {
    @NotNull(groups = {ValidatorGroups.Update.class},
            message = "Не указан идентификатор товара")
    private UUID productId;

    @NotBlank(groups = {ValidatorGroups.Create.class, ValidatorGroups.Update.class},
            message = "Не указано наименование товара")
    private String productName;

    @NotBlank(groups = {ValidatorGroups.Create.class, ValidatorGroups.Update.class},
            message = "Не указано описание товара")
    private String description;

    private String imageSrc;

    @NotNull(groups = {ValidatorGroups.Create.class, ValidatorGroups.Update.class},
            message = "Не указан статус остатка товара")
    private QuantityState quantityState;

    @NotNull(groups = {ValidatorGroups.Create.class, ValidatorGroups.Update.class},
            message = "Не указан статус товара")
    private State productState;

    private ProductCategory productCategory;

    @NotNull(groups = {ValidatorGroups.Create.class, ValidatorGroups.Update.class},
            message = "Не указана цена товара")
    @Min(value = 1,
            groups = {ValidatorGroups.Create.class, ValidatorGroups.Update.class},
            message = "Цена товара не может меньше 1")
    private BigDecimal price;
}
