package org.ecom.backend.products.domain;

import lombok.Getter;
import org.ecom.backend.comons.events.Event;

@Getter
public class ProductCreated extends Event {
    @Getter
    private  Product product;

    public ProductCreated(Object source, Product product) {
        super(source);
        this.product = product;
    }


}
