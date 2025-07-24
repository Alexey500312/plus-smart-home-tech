package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.dto.enums.DirectionSort;

@Data
@Builder(toBuilder = true)
public class SortDto {
    private DirectionSort direction;

    private String property;
}
