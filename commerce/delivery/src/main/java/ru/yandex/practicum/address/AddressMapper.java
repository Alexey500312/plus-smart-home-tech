package ru.yandex.practicum.address;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.AddressDto;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toAddress(AddressDto addressDto);

    AddressDto toAddressDto(Address address);
}
