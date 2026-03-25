package org.ecom.backend.orders.domain;

import jakarta.persistence.*;
import lombok.*;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.shared.valueobjects.Money;

import java.math.BigDecimal;

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
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public OrderItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public Money calculateItemPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
