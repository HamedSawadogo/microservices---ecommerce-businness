package org.ecommerce.productservice.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.productservice.domain.aggregates.Order;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.valueobjects.Money;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer quantity;

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
