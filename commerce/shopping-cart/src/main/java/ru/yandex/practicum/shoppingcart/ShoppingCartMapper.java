package ru.yandex.practicum.shoppingcart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.product.Product;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface ShoppingCartMapper {
    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    @Mapping(source = "products", target = "products", qualifiedByName = "getProducts")
    ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart);

    @Named("getProducts")
    static Map<UUID, Integer> getProducts(Set<Product> products) {
        return products.stream().
                collect(Collectors.toMap(Product::getProductId, Product::getQuantity));
    }
}
