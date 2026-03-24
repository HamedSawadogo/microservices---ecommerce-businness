package org.ecom.backend.orders.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class OrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public OrderItem(OrderProduct product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }


}
