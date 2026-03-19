package org.ecom.backend.payments.application;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.ports.PaymentRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MakeOrderPaymentUseCase {
    private final PaymentRepository paymentRepository;

    public Payment execute(OrderPaymentRequest paymentRequest) {
       var order =
       Payment  payment = new Payment(paymentRequest.order(),
               paymentRequest.amount(),
               null,
               paymentRequest.paymentMethod()
       );

    }
}
