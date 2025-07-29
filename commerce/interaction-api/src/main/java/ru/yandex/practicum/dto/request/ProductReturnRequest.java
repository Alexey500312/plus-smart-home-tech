package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@NotNull
public class ProductReturnRequest {
    @NotNull(message = "Идентификатор заказа не может быть пустым")
    private UUID orderId;

    @NotNull(message = "Список продуктов не может быть пустым")
    private Map<UUID, Integer> products;
}
