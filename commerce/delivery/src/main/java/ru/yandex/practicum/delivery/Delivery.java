package ru.yandex.practicum.delivery;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.address.Address;
import ru.yandex.practicum.dto.enums.DeliveryState;

import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "DELIVERIES")
@ToString
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID deliveryId;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;

    @Column(name = "ORDERID")
    private UUID orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_FROM")
    private Address fromAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_TO")
    private Address toAddress;

    @Column(name = "WEIGHT_TOTAL")
    private Double deliveryWeight;

    @Column(name = "VOLUME_TOTAL")
    private Double deliveryVolume;

    @Column(name = "FRAGILE")
    private Boolean fragile;
}
