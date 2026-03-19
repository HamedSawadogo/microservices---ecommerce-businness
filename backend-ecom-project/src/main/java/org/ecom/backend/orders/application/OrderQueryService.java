package org.ecom.backend.orders.application;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.orders.domain.ports.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        return orderRepository.findAllByCreatedByUserId(1L);
    }

}
