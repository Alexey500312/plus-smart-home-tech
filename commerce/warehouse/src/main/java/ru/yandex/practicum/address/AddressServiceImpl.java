package ru.yandex.practicum.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.AddressDto;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public AddressDto getAddress() {
        return AddressMapper.INSTANSE.toAddressDto(addressRepository.getAddress());
    }
}
