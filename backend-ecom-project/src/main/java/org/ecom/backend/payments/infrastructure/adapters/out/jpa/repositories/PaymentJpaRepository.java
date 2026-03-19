package org.ecom.backend.payments.infrastructure.adapters.out.jpa.repositories;

import org.ecom.backend.payments.infrastructure.adapters.out.jpa.entities.PaymentSchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentSchema, Long> {
}
