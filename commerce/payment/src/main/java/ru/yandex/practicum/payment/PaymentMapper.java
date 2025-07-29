package ru.yandex.practicum.payment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDto toPaymentDto(Payment payment);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "state", expression = "java(ru.yandex.practicum.dto.enums.PaymentState.PENDING)")
    Payment toNewPayment(OrderDto orderDto);
}
