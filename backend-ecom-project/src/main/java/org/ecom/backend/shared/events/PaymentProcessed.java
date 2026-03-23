package org.ecom.backend.shared.events;


import lombok.Getter;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.payments.domain.Payment;

@Getter
public class PaymentProcessed extends Event  {
    private final Order order;
    private final Payment payment;

    public PaymentProcessed(Object source, Order order, Payment payment) {
        super(source);
        this.order = order;
        this.payment = payment;
    }
}
