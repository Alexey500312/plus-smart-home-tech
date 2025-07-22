package ru.yandex.practicum.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

@RequestMapping("/api/v1/warehouse")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void createProduct(@RequestBody @Valid NewProductInWarehouseRequest product) {
        productService.createProduct(product);
    }

    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public BookedProductsDto checkShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        return productService.checkShoppingCart(shoppingCartDto);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void addProduct(@RequestBody @Valid AddProductToWarehouseRequest addProdcut) {
        productService.addProduct(addProdcut);
    }
}
