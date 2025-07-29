package ru.yandex.practicum.payment;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.enums.PaymentState;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "PAYMENTS")
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID paymentId;

    @Column(name = "ORDERID")
    private UUID orderId;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private PaymentState state;

    @Column(name = "DELIVERY_TOTAL")
    private BigDecimal deliveryTotal;

    @Column(name = "PRODUCT_TOTAL")
    private BigDecimal productTotal;

    @Column(name = "FEE_TOTAL")
    private BigDecimal feeTotal;

    @Column(name = "TOTAL_PAYMENT")
    private BigDecimal totalPayment;
}
