package org.ecom.backend.orders.domain;

import jakarta.persistence.*;
import lombok.*;
import org.ecom.backend.products.domain.OrderStatus;
import org.ecom.backend.shared.valueobjects.Money;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Order {
    private Long id;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private Long createdByUserId;
    private List<OrderItem> items;

    public static Order create(String userId,  List<OrderItem> items) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING_FOR_PAYMENTS);
        order.setCreatedByUserId(Long.valueOf(userId));
        order.setItems(items);
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    public Money calculateOrderTotalPrice() {
       // return items.stream().map(OrderItem::calculateItemPrice).reduce(Money.zero(), Money::add);
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
