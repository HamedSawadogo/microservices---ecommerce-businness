package org.ecom.backend.payments.domain;

import lombok.Getter;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.shared.valueobjects.Money;
import java.time.Instant;

@Getter
public class Payment {
    private Long id;
    private final Order order;
    private Instant payAt;
    private final Money amount;
    private PaymentStatus status;
    private Long userId;
    private final PaymentMethod paymentMethod;

    public Payment(Order order,  Money amount, PaymentMethod paymentMethod) {
        this.order = order;;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.paymentMethod = paymentMethod;
    }
}
