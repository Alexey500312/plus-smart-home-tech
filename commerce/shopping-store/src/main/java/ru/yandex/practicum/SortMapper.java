package ru.yandex.practicum;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.product.SortDto;
import ru.yandex.practicum.params.SortParams;

import java.util.List;

@Mapper
public interface SortMapper {
    SortMapper INSTANCE = Mappers.getMapper(SortMapper.class);

    @Mapping(source = "sort", target = "property")
    @Mapping(target = "direction", expression = "java(sortParams.getDirectionOrDefault())")
    SortDto toSortDto(SortParams sortParams);

    List<SortDto> toSortDtoList(List<SortParams> sortParams);
}
