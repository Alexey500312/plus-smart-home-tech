package ru.yandex.practicum.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID>, QuerydslPredicateExecutor<Payment> {
}
