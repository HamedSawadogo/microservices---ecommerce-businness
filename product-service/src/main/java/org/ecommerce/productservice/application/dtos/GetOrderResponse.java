package org.ecommerce.productservice.application.dtos;

import org.ecommerce.productservice.domain.enums.OrderStatus;

public interface GetOrderResponse {
  Long getId();
  OrderStatus getOrderStatus();
}