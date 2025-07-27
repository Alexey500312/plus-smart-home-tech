package ru.yandex.practicum.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.contract.WarehouseOperations;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.request.ShippedToDeliveryRequest;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/v1/warehouse")
@RestController
@RequiredArgsConstructor
@Validated
public class WarehouseController implements WarehouseOperations {
    private final WarehouseService warehouseService;

    @Override
    public AddressDto getAddress() {
        return warehouseService.getAddress();
    }

    @Override
    public void createProduct(NewProductInWarehouseRequest product) {
        warehouseService.createProduct(product);
    }

    @Override
    public BookedProductsDto checkShoppingCart(ShoppingCartDto shoppingCartDto) {
        return warehouseService.checkShoppingCart(shoppingCartDto);
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest addProdcut) {
        warehouseService.addProduct(addProdcut);
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        warehouseService.shippedToDelivery(shippedToDeliveryRequest);
    }

    @Override
    public void returnProducts(Map<UUID, Integer> products) {
        warehouseService.returnProducts(products);
    }

    @Override
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) {
        return warehouseService.assemblyProductForOrderFromShoppingCart(assemblyProductsForOrderRequest);
    }
}
