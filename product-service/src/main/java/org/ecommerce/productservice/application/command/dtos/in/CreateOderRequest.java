package org.ecommerce.productservice.application.command.dtos.in;

import org.ecommerce.productservice.domain.entities.orders.OrderItem;

public record CreateOderRequest(String userId, OrderItem item) {

}