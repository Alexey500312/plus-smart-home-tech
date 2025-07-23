package ru.yandex.practicum.contract;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ShoppingCartOperations {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ShoppingCartDto getActiveShoppingCart(@RequestParam(value = "username") String userName);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    ShoppingCartDto addProductsToShoppingCart(@RequestParam(value = "username") String userName,
                                              @RequestBody Map<UUID, Integer> products);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeShoppingCart(@RequestParam(value = "username") String userName);

    @PostMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    ShoppingCartDto removeProductsFromShoppingCart(@RequestParam(value = "username") String userName,
                                                   @RequestBody Set<UUID> products);

    @PostMapping("/change-quantity")
    @ResponseStatus(HttpStatus.OK)
    ShoppingCartDto changeProductQuantity(@RequestParam(value = "username") String userName,
                                          @RequestBody @Valid ChangeProductQuantityRequest changeProductQuantity);
}
