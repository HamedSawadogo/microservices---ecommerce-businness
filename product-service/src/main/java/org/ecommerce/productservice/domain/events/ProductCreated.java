package org.ecommerce.productservice.domain.events;

import lombok.Getter;
import org.ecommerce.productservice.domain.entities.Product;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductCreated extends ApplicationEvent {
    private  Product product;

    public ProductCreated(Object source, Product product) {
        super(source);
        this.product = product;
    }


}
