package org.ecom.backend.orders.application.eventlisteners;

import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.orders.domain.OrderRepository;
import org.ecom.backend.products.domain.enums.OrderStatus;
import org.ecom.backend.shared.events.PaymentProcessed;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventListener {
    private final OrderRepository orderRepository;

    public OrderEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onOrderPaid(PaymentProcessed paymentProcessed) {
        Order order = paymentProcessed.getOrder();
        order.changeSatus(OrderStatus.ORDERED_SUCCESS_FULLY);
        orderRepository.save(order);
    }
}
