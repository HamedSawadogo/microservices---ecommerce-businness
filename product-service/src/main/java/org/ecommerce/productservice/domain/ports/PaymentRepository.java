package org.ecommerce.productservice.domain.ports;


import org.ecommerce.productservice.domain.entities.Payment;

public interface PaymentRepository  {
    Payment save(Payment payment);
}