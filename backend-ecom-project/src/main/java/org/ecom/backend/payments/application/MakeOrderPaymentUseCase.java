package org.ecom.backend.payments.application;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.orders.domain.repositories.OrderRepository;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.events.PaymentProcessed;
import org.ecom.backend.payments.domain.repositories.PaymentRepository;
import org.ecom.backend.products.domain.enums.OrderStatus;
import org.ecom.backend.shared.exceptions.BussinessException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MakeOrderPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Payment execute(OrderPaymentRequest paymentRequest) {
       return paymentRepository.findByIdempotencyKey(paymentRequest.idemPotencyKey())
               .orElseGet(() -> processPayment(paymentRequest));
    }

    public Payment processPayment(OrderPaymentRequest paymentRequest) {
        Order order = orderRepository.findActiveOrderById(paymentRequest.orderId()).orElseThrow();
        if (order.getOrderStatus() != OrderStatus.PENDING_FOR_PAYMENTS) {
            throw new BussinessException(
                    "Order %d cannot be Paid. Current Status %s"
                    .formatted(paymentRequest.orderId(), order.getOrderStatus())
            );
        }
        Payment  newPayment = new Payment(order.getId(), paymentRequest.amount(), paymentRequest.paymentMethod());
        eventPublisher.publishEvent(new PaymentProcessed(this));
        return paymentRepository.save(newPayment);
    }
}
