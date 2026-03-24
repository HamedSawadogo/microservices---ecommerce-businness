package org.ecom.backend.orders.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.ecom.backend.products.domain.enums.OrderStatus;
import org.ecom.backend.shared.valueobjects.Money;
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

    @Embedded
    private OrderNumber orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private Long createdByUserId;

    @Version
    private int version;

    @JsonIgnoreProperties(value = {"order"}, allowSetters = true)
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> items;

    public static Order create( List<OrderItem> items) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING_FOR_PAYMENTS);
        order.setItems(items);
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    public Money calculateOrderTotalPrice() {
        return items.stream().map(OrderItem::calculateItemPrice).reduce(Money.zero(), Money::add);
        return Money.zero();
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            return;
        }
        item.setOrder(this);
        this.items.add(item);
    }

    public void changeSatus(OrderStatus orderStatus) {
        if (!Objects.equals(orderStatus, this.orderStatus)) {
           this.orderStatus = orderStatus;
        }
    }
}