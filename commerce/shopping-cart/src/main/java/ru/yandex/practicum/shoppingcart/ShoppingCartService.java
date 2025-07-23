package ru.yandex.practicum.shoppingcart;

import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ShoppingCartService {
    ShoppingCartDto getActiveShoppingCart(String username);

    ShoppingCartDto addProductsToShoppingCart(String userName, Map<UUID, Integer> products);

    void removeShoppingCart(String userName);

    ShoppingCartDto removeProductsFromShoppingCart(String userName, Set<UUID> products);

    ShoppingCartDto changeProductQuantity(String userName, ChangeProductQuantityRequest changeProductQuantity);
}
