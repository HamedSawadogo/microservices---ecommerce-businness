package org.ecom.backend.products.domain;

import lombok.Getter;
import org.ecom.backend.shared.events.Event;



public class ProductStatusUpdated extends Event {
    @Getter
    private final Product product;

    public ProductStatusUpdated(Object source, Product product) {
        super(source);
        this.product = product;
    }
}
