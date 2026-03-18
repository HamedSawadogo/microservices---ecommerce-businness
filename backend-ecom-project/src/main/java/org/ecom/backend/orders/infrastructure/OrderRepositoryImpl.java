package org.ecom.backend.orders.infrastructure;

import org.ecom.backend.orders.application.OrderResponse;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.orders.domain.OrderRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public List<Order> findByCreatedByUserIdInPending(Long userId) {
        return List.of();
    }

    @Override
    public List<OrderResponse> findAllByCreatedByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Optional<Order> findActiveOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return null;
    }
}
