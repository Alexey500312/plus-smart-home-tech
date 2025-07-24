package ru.yandex.practicum.product;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductListDto;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.QuantityState;

import java.util.UUID;

public interface ProductService {
    ProductListDto getProductList(ProductCategory category, Pageable pageable);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    Boolean removeProductFromStore(UUID productId);

    Boolean setProductQuantityState(UUID productId, QuantityState quantityState);

    ProductDto findProductById(UUID productId);
}
