package ru.yandex.practicum.error;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.dto.ErrorDto;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.warn("Ошибка в запросе:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Ошибка в запросе")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleConstraintViolation(final ConstraintViolationException e) {
        log.warn("Ошибка в запросе:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Ошибка в запросе")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        log.warn("Ошибка в запросе:", e);
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Ошибка в запросе")
                .message(StringUtils.join(errors, ';'))
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleSpecifiedProductAlreadyInWarehouse(SpecifiedProductAlreadyInWarehouseException e) {
        log.warn("Товар уже зарегистрирован на складе:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Товар уже зарегистрирован на складе")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNoSpecifiedProductInWarehouse(NoSpecifiedProductInWarehouseException e) {
        log.warn("Не найден товар:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND.toString())
                .userMessage("Не найден товар")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleProductInShoppingCartLowQuantityInWarehouse(ProductInShoppingCartLowQuantityInWarehouseException e) {
        log.warn("Товар из корзины не находится в требуемом количестве на складе:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Товар из корзины не находится в требуемом количестве на складе")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception e) {
        log.warn("Непредвиденная ошибка:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .userMessage("Непредвиденная ошибка")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }
}
