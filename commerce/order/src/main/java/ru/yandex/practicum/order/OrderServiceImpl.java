package ru.yandex.practicum.order;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.address.Address;
import ru.yandex.practicum.address.AddressMapper;
import ru.yandex.practicum.address.AddressRepository;
import ru.yandex.practicum.address.QAddress;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.dto.enums.OrderState;
import ru.yandex.practicum.dto.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.request.CreateNewOrderRequest;
import ru.yandex.practicum.dto.request.ProductReturnRequest;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.feign.DeliveryClient;
import ru.yandex.practicum.feign.PaymentClient;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.product.Product;
import ru.yandex.practicum.product.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final WarehouseClient warehouseClient;
    private final PaymentClient paymentClient;
    private final DeliveryClient deliveryClient;

    @Override
    public List<OrderDto> findOrders(String userName, Pageable pageable) {
        Predicate predicate = QOrder.order.userName.equalsIgnoreCase(userName);
        return OrderMapper.INSTANCE.toOrderDtoList(orderRepository.findAll(predicate, pageable).toList());
    }

    @Override
    @Transactional
    public OrderDto createNewOrder(String userName, CreateNewOrderRequest createNewOrderRequest) {
        checkUserName(userName);

        BookedProductsDto bookedProductsDto = warehouseClient.checkShoppingCart(createNewOrderRequest.getShoppingCart());

        //Поиск адреса, если не найден создать новый
        Address address = AddressMapper.INSTANCE.toAddress(createNewOrderRequest.getDeliveryAddress());
        Predicate predicate = QAddress.address.country.equalsIgnoreCase(address.getCountry())
                .and(QAddress.address.city.equalsIgnoreCase(address.getCity()))
                .and(QAddress.address.street.equalsIgnoreCase(address.getStreet()))
                .and(QAddress.address.house.equalsIgnoreCase(address.getHouse()))
                .and(QAddress.address.flat.equalsIgnoreCase(address.getFlat()));
        Iterator<Address> iterator = addressRepository.findAll(predicate).iterator();
        if (iterator.hasNext()) {
            address = iterator.next();
        } else {
            address = addressRepository.save(address);
        }

        //Создать заказ
        Order order = OrderMapper.INSTANCE.toOrder(createNewOrderRequest);
        order.setUserName(userName);
        order.setAddress(address);
        order.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
        order.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        order.setFragile(bookedProductsDto.getFragile());
        order = saveOrder(order);

        //Создать доставку
        DeliveryDto deliveryDto = createDelivery(order);
        order.setDeliveryId(deliveryDto.getDeliveryId());
        order.setDeliveryPrice(deliveryClient.deliveryCost(OrderMapper.INSTANCE.toOrderDto(order)));

        //Создать оплату
        OrderDto orderDto = OrderMapper.INSTANCE.toOrderDto(order);
        order.setProductPrice(paymentClient.calculateProductCost(orderDto));
        PaymentDto paymentDto = paymentClient.payment(orderDto);
        order.setPaymentId(paymentDto.getPaymentId());
        order.setTotalPrice(paymentDto.getTotalPayment());

        return OrderMapper.INSTANCE.toOrderDto(saveOrder(order));
    }

    @Override
    @Transactional
    public OrderDto returnOrder(ProductReturnRequest productReturnRequest) {
        Order order = findByOrderId(productReturnRequest.getOrderId());
        warehouseClient.returnProducts(productReturnRequest.getProducts());
        order.setState(OrderState.PRODUCT_RETURNED);
        return OrderMapper.INSTANCE.toOrderDto(saveOrder(order));
    }

    @Override
    @Transactional
    public OrderDto payOrder(UUID orderId) {
        return OrderMapper.INSTANCE.toOrderDto(changeOrderStatus(orderId, OrderState.PAID));
    }

    @Override
    @Transactional
    public OrderDto payOrderFailed(UUID orderId) {
        return OrderMapper.INSTANCE.toOrderDto(changeOrderStatus(orderId, OrderState.PAYMENT_FAILED));
    }

    @Override
    @Transactional
    public OrderDto deliverOrder(UUID orderId) {
        return OrderMapper.INSTANCE.toOrderDto(changeOrderStatus(orderId, OrderState.DELIVERED));
    }

    @Override
    @Transactional
    public OrderDto deliverOrderFailed(UUID orderId) {
        return OrderMapper.INSTANCE.toOrderDto(changeOrderStatus(orderId, OrderState.DELIVERY_FAILED));
    }

    @Override
    @Transactional
    public OrderDto completeOrder(UUID orderId) {
        return OrderMapper.INSTANCE.toOrderDto(changeOrderStatus(orderId, OrderState.COMPLETED));
    }

    @Override
    @Transactional
    public OrderDto calculateOrderTotal(UUID orderId) {
        Order order = findByOrderId(orderId);
        OrderDto orderDto = OrderMapper.INSTANCE.toOrderDto(order);
        BigDecimal productPrice = paymentClient.calculateProductCost(orderDto);
        BigDecimal totalPrice = paymentClient.calculateTotalCost(orderDto);
        order.setProductPrice(productPrice);
        order.setTotalPrice(totalPrice);
        return OrderMapper.INSTANCE.toOrderDto(saveOrder(order));
    }

    @Override
    @Transactional
    public OrderDto calculateOrderDelivery(UUID orderId) {
        Order order = findByOrderId(orderId);
        BigDecimal deliveryPrice = deliveryClient.deliveryCost(OrderMapper.INSTANCE.toOrderDto(order));
        order.setDeliveryPrice(deliveryPrice);
        return OrderMapper.INSTANCE.toOrderDto(saveOrder(order));
    }

    @Override
    @Transactional
    public OrderDto assemblyOrder(UUID orderId) {
        Order order = changeOrderStatus(orderId, OrderState.ASSEMBLED);
        AssemblyProductsForOrderRequest assemblyProductsForOrderRequest = AssemblyProductsForOrderRequest.builder()
                .orderId(order.getOrderId())
                .products(order.getProducts().stream()
                        .collect(Collectors.toMap(Product::getProductId, Product::getQuantity)))
                .build();
        warehouseClient.assemblyProductForOrderFromShoppingCart(assemblyProductsForOrderRequest);

        return OrderMapper.INSTANCE.toOrderDto(order);
    }

    @Override
    public OrderDto assemblyOrderFailed(UUID orderId) {
        return OrderMapper.INSTANCE.toOrderDto(changeOrderStatus(orderId, OrderState.ASSEMBLY_FAILED));
    }

    private void checkUserName(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new NotAuthorizedUserException("Имя пользователя не должно быть пустым");
        }
    }

    private Order findByOrderId(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Не найден заказ с id " + orderId));
    }

    private Order changeOrderStatus(UUID orderId, OrderState state) {
        Order order = findByOrderId(orderId);
        order.setState(state);
        return saveOrder(order);
    }

    private DeliveryDto createDelivery(Order order) {
        AddressDto fromAddres = warehouseClient.getAddress();
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .orderId(order.getOrderId())
                .fromAddress(fromAddres)
                .toAddress(AddressMapper.INSTANCE.toAddressDto(order.getAddress()))
                .build();
        return deliveryClient.planDelivery(deliveryDto);
    }

    /**
     * Обход ошибки повторного сохранения т.к., hibernate возвращает список товаров как PersistentBag
     */
    private Order saveOrder(Order order) {
        if (order.getOrderId() != null) {
            List<Product> products = new ArrayList<>(order.getProducts());
            order.setProducts(products);
        }
        final UUID orderId = orderRepository.save(order).getOrderId();
        productRepository.saveAll(order.getProducts().stream()
                .peek(p -> p.setOrderId(orderId))
                .toList());
        return findByOrderId(orderId);
    }
}
