package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ProductListDto {
    private List<ProductDto> content;

    private List<SortDto> sort;
}
