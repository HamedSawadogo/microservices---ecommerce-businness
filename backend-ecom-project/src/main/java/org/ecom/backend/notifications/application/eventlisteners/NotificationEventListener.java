package org.ecom.backend.notifications.application.eventlisteners;

import org.ecom.backend.notifications.domain.SendEmailNotificationService;
import org.ecom.backend.products.domain.events.ProductCreated;
import org.ecom.backend.shared.events.PaymentProcessed;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationEventListener {

    private final SendEmailNotificationService emailNotificationService;

    public NotificationEventListener(SendEmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @EventListener
    @Async
    public void onOrderPaid(PaymentProcessed paymentProcessed) {
       List<String> to = new ArrayList<>();
       var content = """
           Bonjour , Votre paiement pour votre commande %s a été effectué avec sucess.
           Items: %s
           Montant: %s  XOF
       """.formatted(
           paymentProcessed.getOrder().getOrderNumber(),
           paymentProcessed.getOrder().getItems(),
           paymentProcessed.getPayment().getAmount().toString()
       );
       var title = "Paiment effectué. Commande %s ".formatted(paymentProcessed.getOrder().getOrderNumber());
       emailNotificationService.sendEmail(to, title, content);
    }

    @EventListener
    @Async
    public void onProductCreated(ProductCreated productCreated) {
        List<String> to = new ArrayList<>();
        var content = """
           Bonjour , Nous vous informons que nous avons un nouveau produit de disponible.
           Produit: %s %s.
           Prix: %s XOF
       """.formatted(
                 productCreated.getProduct().getName(),
                 productCreated.getProduct().getDescription(),
                 productCreated.getProduct().getPrice().toString()
        );
        var title = "Notification nouveau produit";
        emailNotificationService.sendEmail(to, title, content);
    }
}
