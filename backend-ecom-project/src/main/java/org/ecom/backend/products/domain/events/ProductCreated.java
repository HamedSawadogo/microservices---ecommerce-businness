package org.ecom.backend.products.domain.events;

import lombok.Getter;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.shared.events.Event;

@Getter
public class ProductCreated extends Event {
    @Getter
    private Product product;

    public ProductCreated(Object source, Product product) {
        super(source);
        this.product = product;
    }


}
