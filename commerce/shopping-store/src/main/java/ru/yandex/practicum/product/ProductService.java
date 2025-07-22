package ru.yandex.practicum.product;

import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductDtoList;
import ru.yandex.practicum.dto.QuantityState;
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
