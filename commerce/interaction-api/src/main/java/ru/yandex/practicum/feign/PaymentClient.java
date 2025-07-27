package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.contract.PaymentOperations;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient extends PaymentOperations {
}
