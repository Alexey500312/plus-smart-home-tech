package ru.yandex.practicum.shoppingcart;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.enums.State;
import ru.yandex.practicum.dto.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.exception.ShoppingCartProductFoundException;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.product.Product;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final WarehouseClient warehouseClient;

    @Override
    public ShoppingCartDto getActiveShoppingCart(String userName) {
        checkUserName(userName);
        ShoppingCart shoppingCart = findActiveShoppingCart(userName);
        if (shoppingCart == null) {
            shoppingCart = createNewShoppingCart(userName);
        }
        return ShoppingCartMapper.INSTANCE.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto addProductsToShoppingCart(String userName, Map<UUID, Integer> products) {
        checkUserName(userName);
        ShoppingCart shoppingCart = findActiveShoppingCart(userName);
        if (shoppingCart == null) {
            shoppingCart = createNewShoppingCart(userName);
        }
        List<UUID> productsFound = shoppingCart.getProducts().stream()
                .map(Product::getProductId)
                .filter(p -> products.get(p) != null)
                .toList();
        if (!productsFound.isEmpty()) {
            throw new ShoppingCartProductFoundException("Товары уже добавлены в корзину: " + productsFound);
        }

        final UUID shoppingCartId = shoppingCart.getShoppingCartId();
        List<Product> newProducts = products.entrySet().stream()
                .map(p -> Product.builder()
                        .shoppingCartId(shoppingCartId)
                        .productId(p.getKey())
                        .quantity(p.getValue())
                        .build())
                .toList();
        shoppingCart.getProducts().addAll(newProducts);
        warehouseClient.checkShoppingCart(ShoppingCartMapper.INSTANCE.toShoppingCartDto(shoppingCart));
        return ShoppingCartMapper.INSTANCE.toShoppingCartDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void removeShoppingCart(String userName) {
        checkUserName(userName);
        ShoppingCart shoppingCart = findActiveShoppingCart(userName);
        if (shoppingCart == null || shoppingCart.getProducts().isEmpty())
            return;
        shoppingCart.setShoppingCartState(State.DEACTIVATE);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto removeProductsFromShoppingCart(String userName, Set<UUID> products) {
        checkUserName(userName);
        ShoppingCart shoppingCart = findActiveShoppingCart(userName);
        if (shoppingCart == null || shoppingCart.getProducts().isEmpty()) {
            throw new NoProductsInShoppingCartException("Нет искомых товаров в корзине: " + products);
        }
        Set<UUID> shoppingCartProducts = shoppingCart.getProducts().stream()
                .map(Product::getProductId)
                .collect(Collectors.toSet());
        Set<UUID> productsNotFount = products.stream()
                .filter(p -> !shoppingCartProducts.contains(p))
                .collect(Collectors.toSet());
        if (!productsNotFount.isEmpty()) {
            throw new NoProductsInShoppingCartException("Нет искомых товаров в корзине: " + products);
        }
        shoppingCart.setProducts(shoppingCart.getProducts().stream()
                .filter(p -> products.contains(p.getProductId()))
                .collect(Collectors.toSet()));
        return ShoppingCartMapper.INSTANCE.toShoppingCartDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto changeProductQuantity(String userName, ChangeProductQuantityRequest changeProductQuantity) {
        checkUserName(userName);
        ShoppingCart shoppingCart = findActiveShoppingCart(userName);
        if (shoppingCart == null || shoppingCart.getProducts().isEmpty()) {
            throw new NoProductsInShoppingCartException(
                    "Нет искомых товаров в корзине: " + changeProductQuantity.getProductId());
        }
        shoppingCart.setProducts(
                shoppingCart.getProducts().stream()
                        .peek(p -> p.setQuantity(p.getProductId().equals(changeProductQuantity.getProductId())
                                ? changeProductQuantity.getNewQuantity()
                                : p.getQuantity()))
                        .collect(Collectors.toSet()));
        warehouseClient.checkShoppingCart(ShoppingCartMapper.INSTANCE.toShoppingCartDto(shoppingCart));
        return ShoppingCartMapper.INSTANCE.toShoppingCartDto(shoppingCartRepository.save(shoppingCart));
    }

    private ShoppingCart findActiveShoppingCart(String userName) {
        Predicate predicate = QShoppingCart.shoppingCart.userName.eq(userName)
                .and(QShoppingCart.shoppingCart.shoppingCartState.eq(State.ACTIVE));
        Iterator<ShoppingCart> iterator = shoppingCartRepository.findAll(predicate).iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    private ShoppingCart createNewShoppingCart(String userName) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userName(userName)
                .shoppingCartState(State.ACTIVE)
                .products(new HashSet<>())
                .build();
        return shoppingCartRepository.save(shoppingCart);
    }

    private void checkUserName(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new NotAuthorizedUserException("Имя пользователя не должно быть пустым");
        }
    }
}
