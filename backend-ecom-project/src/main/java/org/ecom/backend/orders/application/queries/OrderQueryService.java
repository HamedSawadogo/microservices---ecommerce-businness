package org.ecom.backend.orders.application.queries;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.orders.application.queries.dtos.OrderResponse;
import org.ecom.backend.orders.domain.repositories.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

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
