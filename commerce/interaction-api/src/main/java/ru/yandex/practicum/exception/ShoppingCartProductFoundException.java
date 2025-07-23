package ru.yandex.practicum.exception;

public class ShoppingCartProductFoundException extends RuntimeException {
    public ShoppingCartProductFoundException(String message) {
        super(message);
    }
}
