package org.ecom.backend.orders.application.commands.createoderusecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecom.backend.orders.application.commands.createoderusecase.dtos.CreateOrderItemRequest;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.shared.ResourceCreatedId;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.orders.domain.OrderItem;
import org.ecom.backend.shared.exceptions.BussinessException;
import org.ecom.backend.orders.domain.OrderRepository;
import org.ecom.backend.products.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ResourceCreatedId addOrderItem(CreateOrderItemRequest request) {
        log.info("request to create new Order:  {} ", request);
        Product product = productRepository.findOneForUpdate(request.productId()).orElseThrow();
        if (!product.isAvailableStock() || request.quantity() > product.getAvailableQuantity()) {
            throw new BussinessException("Product stock is out");
        }
        var  currentOderInPendingForPayment = orderRepository.findByCreatedByUserIdInPending();
        if (currentOderInPendingForPayment.isEmpty()) {
            Order order = Order.create(new ArrayList<>(List.of(new OrderItem(product.getId(), request.quantity()))));
            product.decreaseQuantity(request.quantity());
            Order saved = orderRepository.save(order);
            return new ResourceCreatedId(saved.getId());
        }
        var order  = currentOderInPendingForPayment.get();
        product.decreaseQuantity(request.quantity());
        order.addItem(new OrderItem(product.getId(), request.quantity()));
        return new ResourceCreatedId(order.getId());
    }
}
