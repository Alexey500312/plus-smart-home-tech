package ru.yandex.practicum.order;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.request.CreateNewOrderRequest;
import ru.yandex.practicum.dto.request.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> findOrders(String userName, Pageable pageable);

    OrderDto createNewOrder(String userName, CreateNewOrderRequest createNewOrderRequest);

    OrderDto returnOrder(ProductReturnRequest productReturnRequest);

    OrderDto payOrder(UUID orderId);

    OrderDto payOrderFailed(UUID orderId);

    OrderDto deliverOrder(UUID orderId);

    OrderDto deliverOrderFailed(UUID orderId);

    OrderDto completeOrder(UUID orderId);

    OrderDto calculateOrderTotal(UUID orderId);

    OrderDto calculateOrderDelivery(UUID orderId);

    OrderDto assemblyOrder(UUID orderId);

    OrderDto assemblyOrderFailed(UUID orderId);
}
