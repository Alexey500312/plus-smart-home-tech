package ru.yandex.practicum.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "ORDER_PRODUCTS")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDERID")
    private UUID orderId;

    @Column(name = "PRODUCTID")
    private UUID productId;

    @Column(name = "QUANTITY")
    private Integer quantity;
}
