package org.ecommerce.productservice.application.commands.handlers;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.commands.CreateOrderItemRequest;
import org.ecommerce.productservice.application.commands.PaymentRequest;
import org.ecommerce.productservice.application.commands.ResourceCreatedId;
import org.ecommerce.productservice.domain.aggregates.Order;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.entities.OrderItem;
import org.ecommerce.productservice.domain.enums.OrderStatus;
import org.ecommerce.productservice.domain.exceptions.BussinessException;
import org.ecommerce.productservice.domain.ports.OrderRepository;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.ecommerce.productservice.domain.valueobjects.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentCommandService paymentCommandService;

    @Transactional
    public ResourceCreatedId addOrderItem(CreateOrderItemRequest request) {
        Long userId = 1L;
        Product product = productRepository.findOneForUpdate(request.productId()).orElseThrow();
        if (!product.isAvailableStock() || request.quantity() > product.getAvailableQuantity()) {
            throw new BussinessException("Product stock is out");
        }
        List<Order> orders = orderRepository.findByCreatedByUserIdInPending(userId);
        if (orders.isEmpty()) {
            Order order = Order.create(String.valueOf(userId), List.of(new OrderItem(product, request.quantity())));
            product.decreaseQuantity(request.quantity());
            Order saved = orderRepository.save(order);
            return new ResourceCreatedId(saved.getId());
        }
        var currentOderInPendingForPayment = orders.get(0);
        product.decreaseQuantity(request.quantity());
        currentOderInPendingForPayment.addItem(new OrderItem(product, request.quantity()));
        return new ResourceCreatedId(currentOderInPendingForPayment.getId());
    }


    @Transactional
    public Order finalizeOrder(Long orderId, PaymentRequest paymentRequest) {
        Order order = orderRepository.findActiveOrderById(orderId).orElseThrow();
        Money totalPrice = order.calculateOrderTotalPrice();
        if (totalPrice.isGreaterThan(paymentRequest.getAmount())) {
            throw new BussinessException("Inssufficent funds");
        }
        order.setOrderStatus(OrderStatus.ORDERED_SUCCESS_FULLY);
        paymentCommandService.payOrder(paymentRequest);
        return order;
    }
}
