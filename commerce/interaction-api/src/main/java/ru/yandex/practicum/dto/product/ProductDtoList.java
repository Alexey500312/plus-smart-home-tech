package ru.yandex.practicum.dto.product;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ProductDtoList {
    private List<ProductDto> content;

    private List<SortDto> sort;
}
