package ru.yandex.practicum.shoppingcart;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.contract.ShoppingCartOperations;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/api/v1/shopping-cart")
@RestController
@RequiredArgsConstructor
@Validated
public class ShoppingCartController implements ShoppingCartOperations {
    private final ShoppingCartService shoppingCartService;

    @Override
    public ShoppingCartDto getActiveShoppingCart(String userName) {
        return shoppingCartService.getActiveShoppingCart(userName);
    }

    @Override
    public ShoppingCartDto addProductsToShoppingCart(String userName, Map<UUID, Integer> products) {
        return shoppingCartService.addProductsToShoppingCart(userName, products);
    }

    @Override
    public void removeShoppingCart(String userName) {
        shoppingCartService.removeShoppingCart(userName);
    }

    @Override
    public ShoppingCartDto removeProductsFromShoppingCart(String userName, Set<UUID> products) {
        return shoppingCartService.removeProductsFromShoppingCart(userName, products);
    }

    @Override
    public ShoppingCartDto changeProductQuantity(String userName, ChangeProductQuantityRequest changeProductQuantity) {
        return shoppingCartService.changeProductQuantity(userName, changeProductQuantity);
    }
}
