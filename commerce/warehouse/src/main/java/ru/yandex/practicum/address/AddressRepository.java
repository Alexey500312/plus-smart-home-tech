package ru.yandex.practicum.address;

import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.Random;

@Repository
public class AddressRepository {
    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};

    public String getAddress() {
        return ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];
    }
}
