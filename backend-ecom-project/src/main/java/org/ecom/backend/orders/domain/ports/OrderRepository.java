package org.ecom.backend.orders.domain.ports;

import org.ecom.backend.orders.application.OrderResponse;
import org.ecom.backend.orders.domain.Order;
import java.util.List;

public interface OrderRepository {
    List<Order> findByCreatedByUserIdInPending(Long userId);
    List<OrderResponse> findAllByCreatedByUserId(Long userId);
    Order findActiveOrderById(Long id);
    Order save(Order order);
}
