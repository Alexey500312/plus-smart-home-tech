package ru.yandex.practicum.bookingproducts;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "ORDER_BOOKING_PRODUCTS")
@ToString
public class OrderBookingProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOOKINGID")
    private Long orderBookingId;

    @Column(name = "PRODUCTID")
    private UUID productId;

    @Column(name = "QUANTITY")
    private Integer quantity;
}
