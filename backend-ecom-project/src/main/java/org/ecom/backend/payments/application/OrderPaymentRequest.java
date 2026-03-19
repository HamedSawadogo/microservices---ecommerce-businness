package org.ecom.backend.payments.application;

import org.ecom.backend.payments.domain.PaymentMethod;
import org.ecom.backend.shared.valueobjects.Money;

public record OrderPaymentRequest (
        Money amount,
        PaymentMethod paymentMethod,
        String idemPotencyKey,
        Long orderId
) implements PaymentRequest {

}