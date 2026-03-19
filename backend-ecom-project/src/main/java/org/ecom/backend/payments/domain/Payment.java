package org.ecom.backend.payments.domain;

import lombok.Getter;
import org.ecom.backend.shared.valueobjects.Money;
import java.time.Instant;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_seq")
    @SequenceGenerator(name = "pay_seq", sequenceName = "pay_seq", allocationSize = 50)
    private Long id;

    private Long orderId;

    private Instant payAt;

    @Embedded
    private Money amount;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Payment(Long orderId, Money amount, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
}
