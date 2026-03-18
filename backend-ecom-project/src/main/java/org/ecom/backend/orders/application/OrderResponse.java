package org.ecom.backend.orders.application;

import org.ecom.backend.products.domain.OrderStatus;
import java.util.List;

public interface OrderResponse {
  Long getId();
  OrderStatus getOrderStatus();
  List<OrderItemResponse> getItems();
}