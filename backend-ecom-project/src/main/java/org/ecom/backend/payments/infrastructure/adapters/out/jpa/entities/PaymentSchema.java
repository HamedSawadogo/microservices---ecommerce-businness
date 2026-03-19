package org.ecom.backend.payments.infrastructure.adapters.out.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ecom.backend.shared.valueobjects.Money;
import org.ecom.backend.payments.domain.PaymentMethod;
import java.time.Instant;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class PaymentSchema {
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
}
