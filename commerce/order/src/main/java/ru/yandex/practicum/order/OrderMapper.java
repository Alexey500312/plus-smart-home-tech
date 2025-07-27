package ru.yandex.practicum.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.request.CreateNewOrderRequest;
import ru.yandex.practicum.product.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "products", target = "products", qualifiedByName = "getProductsDto")
    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDtoList(List<Order> orders);

    @Mapping(target = "state", expression = "java(ru.yandex.practicum.dto.enums.OrderState.NEW)")
    @Mapping(source = "shoppingCart.shoppingCartId", target = "shoppingCartId")
    @Mapping(source = "shoppingCart.products", target = "products", qualifiedByName = "getProducts")
    Order toOrder(CreateNewOrderRequest orderRequest);

    @Named("getProducts")
    static List<Product> getProducts(Map<UUID, Integer> products) {
        return products.entrySet().stream()
                .filter(p -> p.getKey() != null)
                .map(p -> Product.builder()
                        .productId(p.getKey())
                        .quantity(p.getValue())
                        .build())
                .toList();
    }

    @Named("getProductsDto")
    static Map<UUID, Integer> getProductsDto(List<Product> products) {
        return products.stream()
                .collect(Collectors.toMap(Product::getProductId, Product::getQuantity));
    }
}
