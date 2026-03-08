package org.ecommerce.productservice.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.productservice.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

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

    @JsonIgnoreProperties(value = {"order"}, allowSetters = true)
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
        item.setOrder(this);
        this.items.add(item);
    }
}
