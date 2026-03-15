package org.ecommerce.productservice.domain.services;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.events.ProductCreated;
import org.ecommerce.productservice.domain.events.ProductStatusUpdated;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ProductEventListener {
    private final SimpMessagingTemplate messagingTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onProductCreated(ProductCreated productCreated)  {
        messagingTemplate.convertAndSend("/topic/notifications", productCreated.getProduct());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStatusChange(ProductStatusUpdated productStatusUpdated)  {
        System.err.println("Status changé produit:  " + productStatusUpdated.getProduct());
    }
}
