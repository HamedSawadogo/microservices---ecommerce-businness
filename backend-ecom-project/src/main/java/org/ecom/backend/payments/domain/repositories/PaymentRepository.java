package org.ecom.backend.payments.domain.repositories;
import org.ecom.backend.payments.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);
    Optional<Payment> findByIdempotencyKey(String key);
}