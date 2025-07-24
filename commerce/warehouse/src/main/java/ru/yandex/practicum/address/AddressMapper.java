package ru.yandex.practicum.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.AddressDto;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANSE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "address", target = "country")
    @Mapping(source = "address", target = "city")
    @Mapping(source = "address", target = "street")
    @Mapping(source = "address", target = "house")
    @Mapping(source = "address", target = "flat")
    AddressDto toAddressDto(String address);
}
