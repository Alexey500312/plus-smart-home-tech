package ru.yandex.practicum.warehouse;

import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.request.ShippedToDeliveryRequest;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {
    AddressDto getAddress();

    void createProduct(NewProductInWarehouseRequest product);

    BookedProductsDto checkShoppingCart(ShoppingCartDto shoppingCartDto);

    void addProduct(AddProductToWarehouseRequest addProduct);

    void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest);

    void returnProducts(Map<UUID, Integer> products);

    BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest);
}
