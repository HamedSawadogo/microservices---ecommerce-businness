package org.ecommerce.productservice.application.queries;

import org.ecommerce.productservice.domain.enums.OrderStatus;
import java.util.List;

public interface OrderResponse {
  Long getId();
  OrderStatus getOrderStatus();
  List<OrderItemResponse> getItems();
}