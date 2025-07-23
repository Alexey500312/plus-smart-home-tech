package ru.yandex.practicum.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import ru.yandex.practicum.dto.SortDto;

import java.util.List;

@Mapper
public interface SortMapper {
    SortMapper INSTANCE = Mappers.getMapper(SortMapper.class);

    @Mapping(source = "order.property", target = "property")
    @Mapping(target = "direction", expression = "java(ru.yandex.practicum.dto.enums.DirectionSort.valueOf(order.getDirection().name()))")
    SortDto toSortDto(Sort.Order order);

    List<SortDto> toSortDtoList(List<Sort.Order> orders);
}
