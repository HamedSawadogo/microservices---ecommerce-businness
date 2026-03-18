package org.ecom.backend.orders.domain;

import org.ecom.backend.orders.application.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findByCreatedByUserIdInPending(Long userId);
    List<OrderResponse> findAllByCreatedByUserId(Long userId);
    Optional<Order> findActiveOrderById(Long id);
    Order save(Order order);
}
