package org.ecommerce.productservice.application.commands.handlers;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.commands.PaymentRequest;
import org.ecommerce.productservice.domain.entities.Payment;
import org.ecommerce.productservice.domain.ports.PaymentRepository;
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
