package org.ecom.backend.payments.domain.events;

import org.ecom.backend.shared.events.Event;

public class PaymentProcessed extends Event  {
    public PaymentProcessed(Object source) {
        super(source);
    }
}
