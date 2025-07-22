package ru.yandex.practicum.params;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Pageable {
    private Integer page;
    private Integer size;
    private List<String> sort;

    public Pageable getPageableOrDefault() {
        List<String> sort = this.sort;
        if (sort == null) {
            sort = new ArrayList<>();
            sort.add("productName");
        }

        return Pageable.builder()
                .page(this.page != null ? this.page : 0)
                .size(this.size != null ? this.size : 150)
                .sort(sort)
                .build();
    }
}
