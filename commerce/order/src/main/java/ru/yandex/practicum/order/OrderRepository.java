package ru.yandex.practicum.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>, QuerydslPredicateExecutor<Order> {
}
