package ru.yandex.practicum.order;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.address.Address;
import ru.yandex.practicum.dto.enums.OrderState;
import ru.yandex.practicum.product.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "ORDERS")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID orderId;

    @Column(name = "USERNAME")
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESSID")
    private Address address;

    @Column(name = "SHOPPING_CARTID")
    private UUID shoppingCartId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDERID", insertable = false, updatable = false)
    private List<Product> products;

    @Column(name = "PAYMENTID")
    private UUID paymentId;

    @Column(name = "DELIVERYID")
    private UUID deliveryId;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Column(name = "DELIVERY_WEIGHT")
    private Double deliveryWeight;

    @Column(name = "DELIVERY_VOLUME")
    private Double deliveryVolume;

    @Column(name = "FRAGILE")
    private Boolean fragile;

    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @Column(name = "DELIVERY_PRICE")
    private BigDecimal deliveryPrice;

    @Column(name = "PRODUCT_PRICE")
    private BigDecimal productPrice;
}
