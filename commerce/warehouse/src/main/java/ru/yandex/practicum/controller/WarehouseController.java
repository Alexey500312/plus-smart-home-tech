package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.address.AddressService;
import ru.yandex.practicum.contract.WarehouseOperations;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.product.ProductService;

@RequestMapping("/api/v1/warehouse")
@RestController
@RequiredArgsConstructor
@Validated
public class WarehouseController implements WarehouseOperations {
    private final AddressService addressService;
    private final ProductService productService;

    @Override
    public AddressDto getAddress() {
        return addressService.getAddress();
    }

    @Override
    public void createProduct(NewProductInWarehouseRequest product) {
        productService.createProduct(product);
    }

    @Override
    public BookedProductsDto checkShoppingCart(ShoppingCartDto shoppingCartDto) {
        return productService.checkShoppingCart(shoppingCartDto);
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest addProdcut) {
        productService.addProduct(addProdcut);
    }
}
