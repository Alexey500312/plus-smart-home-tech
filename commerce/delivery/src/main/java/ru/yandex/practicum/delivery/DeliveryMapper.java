package ru.yandex.practicum.delivery;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.DeliveryDto;

@Mapper
public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    @Mapping(target = "deliveryId", ignore = true)
    @Mapping(target = "fromAddress", ignore = true)
    @Mapping(target = "toAddress", ignore = true)
    Delivery toDelivery(DeliveryDto deliveryDto);

    @Mapping(target = "fromAddress", expression = "java(ru.yandex.practicum.address.AddressMapper.INSTANCE.toAddressDto(delivery.getFromAddress()))")
    @Mapping(target = "toAddress", expression = "java(ru.yandex.practicum.address.AddressMapper.INSTANCE.toAddressDto(delivery.getToAddress()))")
    DeliveryDto toDeliveryDto(Delivery delivery);
}
