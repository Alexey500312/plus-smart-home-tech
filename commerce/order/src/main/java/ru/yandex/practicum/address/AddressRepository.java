package ru.yandex.practicum.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddressRepository extends JpaRepository<Address, Long>, QuerydslPredicateExecutor<Address> {
}
