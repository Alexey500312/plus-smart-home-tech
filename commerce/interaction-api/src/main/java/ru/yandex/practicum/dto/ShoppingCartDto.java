package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ShoppingCartDto {
    @NotNull(message = "Не указан идентификатор корзины")
    private UUID shoppingCartId;

    @NotNull(message = "Не указано наполнение корзины")
    private Map<UUID, Integer> products;
}
