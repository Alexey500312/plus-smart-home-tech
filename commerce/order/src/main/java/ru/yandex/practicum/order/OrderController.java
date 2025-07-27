package ru.yandex.practicum.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.contract.OrderOperations;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.request.CreateNewOrderRequest;
import ru.yandex.practicum.dto.request.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/order")
@RestController
@RequiredArgsConstructor
@Validated
public class OrderController implements OrderOperations {
    private final OrderService orderService;

    @Override
    public List<OrderDto> findOrders(String userName, Pageable pageable) {
        return orderService.findOrders(userName, pageable);
    }

    @Override
    public OrderDto createNewOrder(String userName, CreateNewOrderRequest createNewOrderRequest) {
        return orderService.createNewOrder(userName, createNewOrderRequest);
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest productReturnRequest) {
        return orderService.returnOrder(productReturnRequest);
    }

    @Override
    public OrderDto payOrder(UUID orderId) {
        return orderService.payOrder(orderId);
    }

    @Override
    public OrderDto payOrderFailed(UUID orderId) {
        return orderService.payOrderFailed(orderId);
    }

    @Override
    public OrderDto deliverOrder(UUID orderId) {
        return orderService.deliverOrder(orderId);
    }

    @Override
    public OrderDto deliverOrderFailed(UUID orderId) {
        return orderService.deliverOrderFailed(orderId);
    }

    @Override
    public OrderDto completeOrder(UUID orderId) {
        return orderService.completeOrder(orderId);
    }

    @Override
    public OrderDto calculateOrderTotal(UUID orderId) {
        return orderService.calculateOrderTotal(orderId);
    }

    @Override
    public OrderDto calculateOrderDelivery(UUID orderId) {
        return orderService.calculateOrderDelivery(orderId);
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        return orderService.assemblyOrder(orderId);
    }

    @Override
    public OrderDto assemblyOrderFailed(UUID orderId) {
        return orderService.assemblyOrderFailed(orderId);
    }
}
