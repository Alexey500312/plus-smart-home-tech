package ru.yandex.practicum.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.request.CreateNewOrderRequest;
import ru.yandex.practicum.dto.request.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderOperations {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderDto> findOrders(@RequestParam(value = "username") String userName,
                              @PageableDefault(page = 0, size = 150, sort = "orderId") Pageable pageable);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    OrderDto createNewOrder(@RequestParam(value = "username") String userName,
                            @RequestBody @Valid CreateNewOrderRequest createNewOrderRequest);

    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    OrderDto returnOrder(@RequestBody @Valid ProductReturnRequest productReturnRequest);

    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.OK)
    OrderDto payOrder(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/payment/failed")
    @ResponseStatus(HttpStatus.OK)
    OrderDto payOrderFailed(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/delivery")
    @ResponseStatus(HttpStatus.OK)
    OrderDto deliverOrder(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/delivery/failed")
    @ResponseStatus(HttpStatus.OK)
    OrderDto deliverOrderFailed(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/completed")
    @ResponseStatus(HttpStatus.OK)
    OrderDto completeOrder(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/calculate/total")
    @ResponseStatus(HttpStatus.OK)
    OrderDto calculateOrderTotal(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/calculate/delivery")
    @ResponseStatus(HttpStatus.OK)
    OrderDto calculateOrderDelivery(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/assembly")
    @ResponseStatus(HttpStatus.OK)
    OrderDto assemblyOrder(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);

    @PostMapping("/assembly/failed")
    @ResponseStatus(HttpStatus.OK)
    OrderDto assemblyOrderFailed(@RequestBody @Valid @NotNull(message = "Идентификатор заказа не может быть пустым") UUID orderId);
}
