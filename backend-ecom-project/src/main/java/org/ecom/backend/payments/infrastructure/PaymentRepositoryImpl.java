package org.ecom.backend.payments.infrastructure;

import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.ports.PaymentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    @Override
    public Payment save(Payment payment) {
        return null;
    }
}
