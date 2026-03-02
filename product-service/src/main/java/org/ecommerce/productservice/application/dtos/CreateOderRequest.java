package org.ecommerce.productservice.application.dtos;

import org.ecommerce.productservice.domain.entities.orders.OrderItem;

public record CreateOderRequest(String userId, OrderItem item) {

}