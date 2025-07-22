package ru.yandex.practicum.product;

import ru.yandex.practicum.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

public interface ProductService {
    void createProduct(NewProductInWarehouseRequest product);

    BookedProductsDto checkShoppingCart(ShoppingCartDto shoppingCartDto);

    void addProduct(AddProductToWarehouseRequest addProduct);
}
