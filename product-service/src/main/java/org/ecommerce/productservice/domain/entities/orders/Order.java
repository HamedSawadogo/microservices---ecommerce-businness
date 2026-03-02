package org.ecommerce.productservice.domain.entities.orders;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.productservice.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private Long createdByUserId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> items;

    public static Order create(String userId,  List<OrderItem> items) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING_FOR_PAYMENTS);
        order.setCreatedByUserId(Long.valueOf(userId));
        order.setItems(items);
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            return;
        }
        this.items.add(item);
    }
}
