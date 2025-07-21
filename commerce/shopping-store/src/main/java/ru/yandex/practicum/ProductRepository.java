package ru.yandex.practicum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, QuerydslPredicateExecutor<Product> {
}
