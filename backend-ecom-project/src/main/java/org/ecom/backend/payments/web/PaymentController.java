package org.ecom.backend.payments.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecom.backend.payments.application.MakeOrderPaymentUseCase;
import org.ecom.backend.payments.application.OrderPaymentRequest;
import org.ecom.backend.payments.domain.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final MakeOrderPaymentUseCase makeOrderPaymentUseCase;

    @PostMapping
    public ResponseEntity<Payment> makePayment(
      @RequestBody @Valid OrderPaymentRequest request,
      @RequestHeader(name = "idempotency-key") String idemPotencyKey
    ) {
        var newReq = new OrderPaymentRequest(request.amount(), request.paymentMethod(), idemPotencyKey, request.orderId());
        Payment payment = makeOrderPaymentUseCase.execute(newReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}