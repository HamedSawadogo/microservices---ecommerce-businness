package org.ecom.backend.payments.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.comons.valueobjects.Money;
import java.time.Instant;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_seq")
    @SequenceGenerator(name = "pay_seq", sequenceName = "pay_seq", allocationSize = 50)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    private Instant payAt;

    @Embedded
    private Money amount;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Payment(Instant payAt, Money amount, Long userId, PaymentMethod paymentMethod, Order order) {
        this.payAt = payAt;
        this.amount = amount;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.order = order;
    }
}
