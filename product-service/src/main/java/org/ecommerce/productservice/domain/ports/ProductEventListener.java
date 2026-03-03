package org.ecommerce.productservice.domain.ports;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.events.products.ProductCreated;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ProductEventListener {
    private final EmailService emailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onProductCreated(ProductCreated productCreated) throws Exception {
        emailService.sendEmail(productCreated.toString());
    }
}
