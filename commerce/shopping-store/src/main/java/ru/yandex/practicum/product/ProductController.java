package ru.yandex.practicum.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.contract.ShoppingStoreOperations;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductListDto;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.QuantityState;

import java.util.UUID;

@RequestMapping("/api/v1/shopping-store")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductController implements ShoppingStoreOperations {
    private final ProductService productService;

    @Override
    public ProductListDto findProductByCategory(ProductCategory category, Pageable pageable) {
        return productService.getProductList(category, pageable);
    }

    @Override
    public ProductDto createProduct(ProductDto createCategoryDto) {
        return productService.createProduct(createCategoryDto);
    }

    @Override
    public ProductDto updateProduct(ProductDto createCategoryDto) {
        return productService.updateProduct(createCategoryDto);
    }

    @Override
    public Boolean removeProductFromStore(UUID productId) {
        return productService.removeProductFromStore(productId);
    }

    @Override
    public Boolean setProductQuantityState(UUID productId, QuantityState quantityState) {
        return productService.setProductQuantityState(productId, quantityState);
    }

    @Override
    public ProductDto findProductById(UUID productId) {
        return productService.findProductById(productId);
    }
}
