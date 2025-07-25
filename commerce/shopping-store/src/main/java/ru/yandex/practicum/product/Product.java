package ru.yandex.practicum.product;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.QuantityState;
import ru.yandex.practicum.dto.enums.State;

import java.math.BigDecimal;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID productId;

    @Column(name = "NAME")
    private String productName;

    private String description;

    @Column(name = "IMAGE_SRC")
    private String imageSrc;

    @Column(name = "QUANTITY_STATE")
    @Enumerated(EnumType.STRING)
    private QuantityState quantityState;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private State productState;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    private BigDecimal price;
}
