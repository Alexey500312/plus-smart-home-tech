package ru.yandex.practicum.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID>, QuerydslPredicateExecutor<ShoppingCart> {
}
