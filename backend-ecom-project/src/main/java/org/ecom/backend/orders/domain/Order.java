package org.ecom.backend.orders.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.ecom.backend.products.domain.enums.OrderStatus;
import org.ecom.backend.shared.exceptions.BussinessException;
import org.ecom.backend.shared.valueobjects.Money;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
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
    private  List<OrderItem> items = new ArrayList<>();

    public static Order create(List<OrderItem> items) {
        Order order = new Order();
        order.changeStatus(OrderStatus.PENDING_FOR_PAYMENTS);
        order.updateOrderItems(items);
        order.updateOrderCreationDate(LocalDateTime.now());
        return order;
    }

    public void updateOrderCreationDate(LocalDateTime createdAt) {
        if (createdAt == null) {
            this.createdAt = LocalDateTime.now();
            return;
        }
        this.createdAt = createdAt;
    }

    public void updateOrderItems(List<OrderItem> orderItems) {
        if (orderItems == null) {
            this.items = new ArrayList<>();
            return;
        }

        for (var item:  orderItems) {
           addItem(item);
        }
    }

    public final Money calculateOrderTotalPrice() {
        return items.stream().map(OrderItem::calculateItemPrice).reduce(Money.zero(), Money::add);
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            return;
        }
        item.setOrder(this);
        this.items.add(item);
    }

    public void changeStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            throw new BussinessException("Status cannot be null");
        }

        if (!Objects.equals(orderStatus, this.orderStatus)) {
           this.orderStatus = orderStatus;
        }
    }
}