package org.ecom.backend.orders.domain;

import lombok.*;

@Getter
@Setter
public class OrderItem {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Order order;

    public OrderItem(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
