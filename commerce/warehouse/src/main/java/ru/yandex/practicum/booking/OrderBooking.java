package ru.yandex.practicum.booking;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.bookingproducts.OrderBookingProduct;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "ORDER_BOOKINGS")
@ToString
public class OrderBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDERID")
    private UUID orderId;

    @Column(name = "DELIVERYID")
    private UUID deliveryId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOKINGID", insertable = false, updatable = false)
    private List<OrderBookingProduct> products;
}
