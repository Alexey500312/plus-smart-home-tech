package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.contract.OrderOperations;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderClient extends OrderOperations {
}
