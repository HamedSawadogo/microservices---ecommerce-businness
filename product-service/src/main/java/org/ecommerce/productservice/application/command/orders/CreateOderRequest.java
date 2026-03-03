package org.ecommerce.productservice.application.command.orders;

import org.ecommerce.productservice.domain.entities.orders.OrderItem;

public record CreateOderRequest(String userId, OrderItem item) {

}