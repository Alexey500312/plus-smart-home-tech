package ru.yandex.practicum.contract;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.request.NewProductInWarehouseRequest;

public interface WarehouseOperations {
    @GetMapping("/address")
    @ResponseStatus(HttpStatus.OK)
    AddressDto getAddress();

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    void createProduct(@RequestBody @Valid NewProductInWarehouseRequest product);

    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    BookedProductsDto checkShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto);

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    void addProduct(@RequestBody @Valid AddProductToWarehouseRequest addProdcut);
}
