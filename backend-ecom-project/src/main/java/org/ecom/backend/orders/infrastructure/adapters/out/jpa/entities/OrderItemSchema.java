package org.ecom.backend.orders.infrastructure.adapters.out.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class OrderItemSchema {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

//   @ManyToOne(fetch = FetchType.LAZY)
//   private Product product;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderSchema order;

}
