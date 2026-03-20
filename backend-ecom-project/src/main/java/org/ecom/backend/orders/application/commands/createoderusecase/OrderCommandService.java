package org.ecom.backend.orders.application.commands.createoderusecase;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.orders.application.commands.createoderusecase.dtos.CreateOrderItemRequest;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.shared.ResourceCreatedId;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.orders.domain.OrderItem;
import org.ecom.backend.shared.exceptions.BussinessException;
import org.ecom.backend.orders.domain.repositories.OrderRepository;
import org.ecom.backend.products.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ResourceCreatedId addOrderItem(CreateOrderItemRequest request) {
        Long userId = 1L;
        Product product = productRepository.findOneForUpdate(request.productId()).orElseThrow();
        if (!product.isAvailableStock() || request.quantity() > product.getAvailableQuantity()) {
            throw new BussinessException("Product stock is out");
        }
        List<Order> orders = orderRepository.findByCreatedByUserIdInPending(userId);
        if (orders.isEmpty()) {
            Order order = Order.create(String.valueOf(userId), List.of(new OrderItem(product.getId(), request.quantity())));
            product.decreaseQuantity(request.quantity());
            Order saved = orderRepository.save(order);
            return new ResourceCreatedId(saved.getId());
        }
        var currentOderInPendingForPayment = orders.get(0);
        product.decreaseQuantity(request.quantity());
        currentOderInPendingForPayment.addItem(new OrderItem(product.getId(), request.quantity()));
        return new ResourceCreatedId(currentOderInPendingForPayment.getId());
    }
}
