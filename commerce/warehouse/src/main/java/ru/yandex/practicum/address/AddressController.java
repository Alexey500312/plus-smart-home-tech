package ru.yandex.practicum.address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.AddressDto;

@RequestMapping("/api/v1/warehouse")
@RestController
@RequiredArgsConstructor
@Validated
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/address")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddress() {
        return addressService.getAddress();
    }
}
