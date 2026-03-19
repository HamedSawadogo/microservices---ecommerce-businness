package org.ecom.backend.payments.application;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.orders.domain.ports.OrderRepository;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.events.PaymentProcessed;
import org.ecom.backend.payments.domain.ports.PaymentRepository;
import org.ecom.backend.products.domain.OrderStatus;
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
       var payment = paymentRepository.findByIdempotencyKey(paymentRequest.idemPotencyKey());
       if (payment.isPresent()) {
          return payment.get();
       }

       Order order = orderRepository.findActiveOrderById(paymentRequest.orderId()).orElseThrow();
       if (order.getOrderStatus() != OrderStatus.PENDING_FOR_PAYMENTS) {
         throw new BussinessException("Order should be in Pending for Payments");
       }
       Payment  newPayment = new Payment(order, paymentRequest.amount(), paymentRequest.paymentMethod());
       eventPublisher.publishEvent(new PaymentProcessed(this));
       return paymentRepository.save(newPayment);
    }
}
