package ru.yandex.practicum.bookingproducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderBookingProductRepository extends JpaRepository<OrderBookingProduct, Long>, QuerydslPredicateExecutor<OrderBookingProduct> {
}
