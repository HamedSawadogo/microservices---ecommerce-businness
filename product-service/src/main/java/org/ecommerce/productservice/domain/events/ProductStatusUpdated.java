package org.ecommerce.productservice.domain.events;

import lombok.Getter;
import org.ecommerce.productservice.domain.aggregates.Product;

public class ProductStatusUpdated extends Event {
    @Getter
    private final Product product;

    public ProductStatusUpdated(Object source, Product product) {
        super(source);
        this.product = product;
    }
}
