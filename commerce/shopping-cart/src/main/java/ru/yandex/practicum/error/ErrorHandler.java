package ru.yandex.practicum.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.dto.ErrorDto;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.exception.ShoppingCartProductFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {
    private final ObjectMapper mapper;

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
    public ErrorDto handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("Пустое тело запроса:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Пустое тело запроса")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDto> handleFeign(FeignException e) throws Exception {
        return new ResponseEntity<>(mapper.readValue(e.contentUTF8(), ErrorDto.class), HttpStatus.valueOf(e.status()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleNotAuthorizedUser(NotAuthorizedUserException e) {
        log.warn("Имя пользователя не должно быть пустым:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.UNAUTHORIZED.toString())
                .userMessage("Имя пользователя не должно быть пустым")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleShoppingCartProductFound(ShoppingCartProductFoundException e) {
        log.warn("Товары уже добавлены в корзину:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Товары уже добавлены в корзину")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleNoProductsInShoppingCart(NoProductsInShoppingCartException e) {
        log.warn("Нет искомых товаров в корзине:", e);
        return ErrorDto.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .userMessage("Нет искомых товаров в корзине")
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
