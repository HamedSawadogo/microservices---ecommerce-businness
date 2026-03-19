package org.ecom.backend.payments.domain.ports;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.PaymentStatus;

import java.util.List;

public interface PaymentRepository  {
    Payment save(Payment payment);
    List<Payment> findAllPayments(int page, int size);
    List<Payment> findAllByStatus(PaymentStatus status);
}