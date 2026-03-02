package org.ecommerce.productservice.application.repositories;

import org.ecommerce.productservice.domain.entities.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
