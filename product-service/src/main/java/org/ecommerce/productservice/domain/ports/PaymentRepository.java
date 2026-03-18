package org.ecommerce.productservice.domain.ports;

import org.ecommerce.productservice.domain.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}