package org.ecommerce.productservice.infrastructure.out.persistence.adapters;

import org.ecommerce.productservice.domain.entities.Payment;
import org.ecommerce.productservice.domain.ports.PaymentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    @Override
    public Payment save(Payment payment) {
        return null;
    }
}
