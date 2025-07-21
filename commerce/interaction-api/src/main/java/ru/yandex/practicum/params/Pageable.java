package ru.yandex.practicum.params;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.dto.product.DirectionSort;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Pageable {
    private Integer page;
    private Integer size;
    private List<String> sort;
    private List<DirectionSort> direction;

    public Pageable getPageableOrDefault() {
        List<String> sort = this.sort;
        if (sort == null) {
            sort = new ArrayList<>();
            sort.add("productName");
        }
        List<DirectionSort> direction = this.direction;
        if (direction == null) {
            direction = new ArrayList<>();
            direction.add(DirectionSort.ASC);
        }

        return Pageable.builder()
                .page(this.page != null ? this.page : 0)
                .size(this.size != null ? this.size : 150)
                .sort(sort)
                .direction(direction)
                .build();
    }

    public List<SortParams> getSortParams() {
        List<SortParams> sortParams = new ArrayList<>();
        for (int i = 0; i < this.sort.size(); i++) {
            if (sort.get(i) != null) {
                DirectionSort directionSort = this.direction.size() >= i && this.direction.get(i) != null
                        ? this.direction.get(i)
                        : DirectionSort.ASC;
                sortParams.add(SortParams.builder()
                        .sort(sort.get(i))
                        .direction(directionSort)
                        .build());
            }
        }
        return sortParams;
    }
}
