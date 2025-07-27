package ru.yandex.practicum.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderBookingRepository extends JpaRepository<OrderBooking, Long>, QuerydslPredicateExecutor<OrderBooking> {
}
