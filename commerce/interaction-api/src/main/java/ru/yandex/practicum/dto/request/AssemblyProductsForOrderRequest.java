package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class AssemblyProductsForOrderRequest {
    @NotNull(message = "Список товаров не может быть пустым")
    private Map<UUID, Integer> products;

    @NotNull(message = "ИД заказа не может быть пустым")
    private UUID orderId;
}
