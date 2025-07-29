package ru.yandex.practicum.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentOperations {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    PaymentDto payment(@RequestBody @Valid OrderDto orderDto);

    @PostMapping("/totalCost")
    @ResponseStatus(HttpStatus.OK)
    BigDecimal calculateTotalCost(@RequestBody @Valid OrderDto orderDto);

    @PostMapping("/refund")
    @ResponseStatus(HttpStatus.OK)
    void refund(@RequestBody @Valid @NotNull(message = "ИД оплаты не может быть пустым") UUID paymentId);

    @PostMapping("/productCost")
    @ResponseStatus(HttpStatus.OK)
    BigDecimal calculateProductCost(@RequestBody @Valid OrderDto orderDto);

    @PostMapping("/failed")
    @ResponseStatus(HttpStatus.OK)
    void payFailed(@RequestBody @Valid @NotNull(message = "ИД оплаты не может быть пустым") UUID paymentId);
}
