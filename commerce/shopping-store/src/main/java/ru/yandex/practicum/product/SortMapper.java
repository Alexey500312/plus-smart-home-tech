package ru.yandex.practicum.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.product.SortDto;

import java.util.List;

@Mapper
public interface SortMapper {
    SortMapper INSTANCE = Mappers.getMapper(SortMapper.class);

    @Mapping(source = "sort", target = "property")
    @Mapping(target = "direction", expression = "java(ru.yandex.practicum.dto.product.DirectionSort.ASC)")
    SortDto toSortDto(String sort);

    List<SortDto> toSortDtoList(List<String> sorts);
}
