package ru.yandex.practicum.product;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createProduct(NewProductInWarehouseRequest product) {
        if (productRepository.findById(product.getProductId()).isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException(
                    "Товар с id = " + product.getProductId() + " уже зарегистрирован на складе");
        }
        productRepository.save(ProductMapper.INSTANCE.toProduct(product));
    }

    @Override
    public BookedProductsDto checkShoppingCart(ShoppingCartDto shoppingCartDto) {
        Predicate predicate = QProduct.product.productId.in(shoppingCartDto.getProducts().keySet());
        Map<UUID, Integer> products = new HashMap<>();
        final BookedProductsDto booked = BookedProductsDto.builder()
                .deliveryWeight(0.)
                .deliveryVolume(0.)
                .fragile(false)
                .build();
        productRepository.findAll(predicate).forEach(p -> {
            Integer quantity = shoppingCartDto.getProducts().get(p.getProductId());
            products.put(p.getProductId(), p.getQuantity());
            booked.setDeliveryVolume(
                    booked.getDeliveryVolume() + (p.getHeight() * p.getWeight() * p.getDepth() * quantity));
            booked.setDeliveryWeight(booked.getDeliveryWeight() + (p.getWeight() * quantity));
            booked.setFragile(Boolean.TRUE.equals(booked.getFragile()) || Boolean.TRUE.equals(p.getFragile()));
        });
        List<UUID> productIdsNotFound = shoppingCartDto.getProducts().keySet().stream()
                .filter(p -> products.get(p) == null)
                .toList();
        if (!productIdsNotFound.isEmpty()) {
            throw new NoSpecifiedProductInWarehouseException("Не найдены товары: " + productIdsNotFound);
        }
        List<UUID> productIdsEnded = shoppingCartDto.getProducts().entrySet().stream()
                .filter(p -> products.get(p.getKey()).compareTo(p.getValue()) < 0)
                .map(Map.Entry::getKey)
                .toList();
        if (!productIdsEnded.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouseException(
                    "Товар из корзины не находится в требуемом количестве на складе для id " + productIdsEnded);
        }
        return booked;
    }

    @Override
    @Transactional
    public void addProduct(AddProductToWarehouseRequest addProduct) {
        Product product = getProductById(addProduct.getProductId());
        product.setQuantity(product.getQuantity() + addProduct.getQuantity());
        productRepository.save(product);
    }

    private Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("Не найден товар с id " + productId));
    }
}
