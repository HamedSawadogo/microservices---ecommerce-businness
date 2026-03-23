package org.ecom.backend.payments.domain.repositories;
import jakarta.persistence.LockModeType;
import org.ecom.backend.payments.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Payment> findByIdempotencyKey(String key);
}