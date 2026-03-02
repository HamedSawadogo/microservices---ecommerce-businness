package org.ecommerce.productservice.domain.entities.orders;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.productservice.domain.entities.products.Product;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

}
