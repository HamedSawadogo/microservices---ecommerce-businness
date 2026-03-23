package org.ecom.backend.orders.application.queries;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.orders.domain.OrderRepository;
import org.ecom.backend.shared.exceptions.BussinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAllByCreatedByUserId(1L);
    }

    public Order findActiveOrderById() {
        var order = orderRepository.findActiveOrderById();
        return order.orElseThrow(() -> new BussinessException("Not active Order found"));
    }
}
