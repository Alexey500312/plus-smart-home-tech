package ru.yandex.practicum.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.request.ShippedToDeliveryRequest;

import java.util.Map;
import java.util.UUID;

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

    @PostMapping("/shipped")
    @ResponseStatus(HttpStatus.OK)
    void shippedToDelivery(@RequestBody @Valid ShippedToDeliveryRequest shippedToDeliveryRequest);

    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    void returnProducts(@RequestBody @Valid @NotNull(message = "Список товаров для возврата не может быть пустым") Map<UUID, Integer> products);

    @PostMapping("/assembly")
    @ResponseStatus(HttpStatus.OK)
    BookedProductsDto assemblyProductForOrderFromShoppingCart(@RequestBody @Valid AssemblyProductsForOrderRequest assemblyProductsForOrderRequest);
}
