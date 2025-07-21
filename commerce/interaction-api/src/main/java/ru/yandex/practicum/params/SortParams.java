package ru.yandex.practicum.params;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.dto.product.DirectionSort;

@Data
@Builder(toBuilder = true)
public class SortParams {
    private String sort;
    private DirectionSort direction;

    public DirectionSort getDirectionOrDefault() {
        return this.direction != null ? this.direction : DirectionSort.ASC;
    }
}
