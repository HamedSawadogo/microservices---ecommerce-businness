package org.ecom.backend.payments.application;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.payments.domain.Payment;
import org.ecom.backend.payments.domain.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentCommandService {
  private final PaymentRepository paymentRepository;

  @Transactional
  public Payment payOrder(PaymentRequest paymentRequest) {
      Payment payment = new Payment(Instant.now(),
              paymentRequest.getAmount(), null, paymentRequest.getPaymentMethod(),
              paymentRequest.getOrder());
      paymentRepository.save(payment);
      return payment;
  }
}
