package org.ecommerce.productservice.application.commands;

import org.ecommerce.productservice.domain.entities.OrderItem;

public record CreateOderRequest(String userId, OrderItem item) {

}