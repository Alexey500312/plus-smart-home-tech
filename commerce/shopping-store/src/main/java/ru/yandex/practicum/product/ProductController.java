package ru.yandex.practicum.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.ProductDtoList;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.params.Pageable;
import ru.yandex.practicum.validation.ValidatorGroups;

import java.util.UUID;

@RequestMapping("/api/v1/shopping-store")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDtoList findProductByCategory(@RequestParam("category") @NotNull ProductCategory category,
                                                Pageable pageable) {
        return productService.getProductList(category, pageable.getPageableOrDefault());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated({ValidatorGroups.Create.class})
    public ProductDto createProduct(@RequestBody @Valid ProductDto createCategoryDto) {
        return productService.createProduct(createCategoryDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Validated({ValidatorGroups.Update.class})
    public ProductDto updateProduct(@RequestBody @Valid ProductDto createCategoryDto) {
        return productService.updateProduct(createCategoryDto);
    }

    @PostMapping("/removeProductFromStore")
    @ResponseStatus(HttpStatus.OK)
    public Boolean removeProductFromStore(@RequestBody UUID productId) {
        return productService.removeProductFromStore(productId);
    }

    @PostMapping("/quantityState")
    @ResponseStatus(HttpStatus.OK)
    public Boolean setProductQuantityState(@RequestParam("productId") @NotNull UUID productId,
                                           @RequestParam("quantityState") @NotNull QuantityState quantityState) {
        return productService.setProductQuantityState(productId, quantityState);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findProductById(@PathVariable("productId") @NotNull UUID productId) {
        return productService.findProductById(productId);
    }
}
