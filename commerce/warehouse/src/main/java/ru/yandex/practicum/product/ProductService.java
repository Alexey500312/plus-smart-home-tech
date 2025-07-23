package ru.yandex.practicum.product;

import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.request.NewProductInWarehouseRequest;

public interface ProductService {
    void createProduct(NewProductInWarehouseRequest product);

    BookedProductsDto checkShoppingCart(ShoppingCartDto shoppingCartDto);

    void addProduct(AddProductToWarehouseRequest addProduct);
}
