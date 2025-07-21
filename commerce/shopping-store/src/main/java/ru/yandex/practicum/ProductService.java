package ru.yandex.practicum;

import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.ProductDtoList;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.params.Pageable;

import java.util.UUID;

public interface ProductService {
    ProductDtoList getProductList(ProductCategory category, Pageable pageable);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    Boolean removeProductFromStore(UUID productId);

    Boolean setProductQuantityState(UUID productId, QuantityState quantityState);

    ProductDto findProductById(UUID productId);
}
