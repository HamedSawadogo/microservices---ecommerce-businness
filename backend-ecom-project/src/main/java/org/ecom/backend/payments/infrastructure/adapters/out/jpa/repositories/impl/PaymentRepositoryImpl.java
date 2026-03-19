package org.ecom.backend.payments.infrastructure.adapters.out.jpa.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.ports.PaymentRepository;
import org.ecom.backend.payments.domain.PaymentStatus;
import org.ecom.backend.payments.infrastructure.adapters.out.jpa.repositories.PaymentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return null;
    }

    @Override
    public List<Payment> findAllPayments(int page, int size) {
        return List.of();
    }

    @Override
    public List<Payment> findAllByStatus(PaymentStatus status) {
        return List.of();
    }
}
