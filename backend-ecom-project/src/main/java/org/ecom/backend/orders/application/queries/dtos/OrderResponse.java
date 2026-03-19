package org.ecom.backend.orders.application.queries.dtos;

import org.ecom.backend.products.domain.enums.OrderStatus;
import java.util.List;

public interface OrderResponse {
  Long getId();
  OrderStatus getOrderStatus();
  List<OrderItemResponse> getItems();
}