package org.ecom.backend.payments.domain.repositories;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);
    List<Payment> findAllPayments(int page, int size);
    List<Payment> findAllByStatus(PaymentStatus status);
    Optional<Payment> findByIdempotencyKey(String s);
}