package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.ShoppingCartDto;

@Data
@Builder(toBuilder = true)
public class CreateNewOrderRequest {
    @NotNull(message = "Корзина товаров не может быть пустой")
    private ShoppingCartDto shoppingCart;

    @NotNull(message = "Адрес доставки не может быть пустым")
    private AddressDto deliveryAddress;
}
