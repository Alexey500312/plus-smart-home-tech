package ru.yandex.practicum.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductListDto;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.QuantityState;
import ru.yandex.practicum.validation.ValidatorGroups;

import java.util.UUID;

public interface ShoppingStoreOperations {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ProductListDto findProductByCategory(@RequestParam("category") @NotNull ProductCategory category,
                                         @PageableDefault(page = 0, size = 150, sort = "productName") Pageable pageable);

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated({ValidatorGroups.Create.class})
    ProductDto createProduct(@RequestBody @Valid ProductDto createCategoryDto);

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Validated({ValidatorGroups.Update.class})
    ProductDto updateProduct(@RequestBody @Valid ProductDto createCategoryDto);

    @PostMapping("/removeProductFromStore")
    @ResponseStatus(HttpStatus.OK)
    Boolean removeProductFromStore(@RequestBody UUID productId);

    @PostMapping("/quantityState")
    @ResponseStatus(HttpStatus.OK)
    Boolean setProductQuantityState(@RequestParam("productId") @NotNull UUID productId,
                                    @RequestParam("quantityState") @NotNull QuantityState quantityState);

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    ProductDto findProductById(@PathVariable("productId") @NotNull UUID productId);
}
