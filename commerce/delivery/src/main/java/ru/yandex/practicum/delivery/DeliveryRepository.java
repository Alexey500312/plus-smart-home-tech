package ru.yandex.practicum.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID>, QuerydslPredicateExecutor<Delivery> {
}
