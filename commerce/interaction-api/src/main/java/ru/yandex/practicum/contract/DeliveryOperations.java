package ru.yandex.practicum.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryOperations {
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    DeliveryDto planDelivery(@RequestBody @Valid DeliveryDto deliveryDto);

    @PostMapping("/successful")
    @ResponseStatus(HttpStatus.OK)
    void delivery(@RequestBody @Valid @NotNull(message = "ИД заказа не может быть пустым") UUID orderId);

    @PostMapping("/picked")
    @ResponseStatus(HttpStatus.OK)
    void shippedToDelivery(@RequestBody @Valid @NotNull(message = "ИД заказа не может быть пустым") UUID orderId);

    @PostMapping("/failed")
    @ResponseStatus(HttpStatus.OK)
    void deliveryFailed(@RequestBody @Valid @NotNull(message = "ИД заказа не может быть пустым") UUID orderId);

    @PostMapping("/cost")
    @ResponseStatus(HttpStatus.OK)
    BigDecimal deliveryCost(@RequestBody @Valid OrderDto orderDto);
}
