package ru.yandex.practicum.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "products")
@ToString
public class Product {
    @Id
    @Column(name = "ID")
    private UUID productId;

    private Double width;

    private Double height;

    private Double depth;

    private Boolean fragile;

    private Double weight;

    private Integer quantity;
}