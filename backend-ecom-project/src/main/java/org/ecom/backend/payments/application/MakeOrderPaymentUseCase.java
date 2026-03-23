package org.ecom.backend.payments.application;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.orders.application.queries.OrderQueryService;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.shared.events.PaymentProcessed;
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
    private final OrderQueryService orderQueryService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Payment execute(OrderPaymentRequest paymentRequest) {
       var payment =   paymentRepository.findByIdempotencyKey(paymentRequest.idemPotencyKey());
       return payment.orElseGet(() -> processPayment(paymentRequest));
    }

    private Payment processPayment(OrderPaymentRequest paymentRequest) {
        Order order = orderQueryService.findActiveOrderById();
        if (order.getOrderStatus() != OrderStatus.PENDING_FOR_PAYMENTS) {
            throw new BussinessException(
                    "Order %d cannot be Paid. Current Status %s"
                    .formatted(paymentRequest.orderId(),
                     order.getOrderStatus()
                )
            );
        }
        if (!order.calculateOrderTotalPrice().isEquals(paymentRequest.amount())) {
            throw new BussinessException("Invalid Payment amount");
        }
        Payment payment = paymentRepository.save(new Payment(order.getId(), paymentRequest.amount(), paymentRequest.paymentMethod()));
        var paymentProcessedEvent = new PaymentProcessed(
         this,
          order,
          payment
        );
        eventPublisher.publishEvent(paymentProcessedEvent);
        return payment;
    }
}
