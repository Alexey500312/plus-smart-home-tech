package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BookedProductsDto {
    private Double deliveryWeight;

    private Double deliveryVolume;

    private Boolean fragile;
}
