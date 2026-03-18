package org.ecommerce.productservice.domain.ports;

import org.ecommerce.productservice.application.queries.OrderResponse;
import org.ecommerce.productservice.domain.aggregates.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findByCreatedByUserIdInPending(Long userId);
    List<OrderResponse> findAllByCreatedByUserId(Long userId);
    Optional<Order> findActiveOrderById(Long id);
    Order save(Order order);
}
