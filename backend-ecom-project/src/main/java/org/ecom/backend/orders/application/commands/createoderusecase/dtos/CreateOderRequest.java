package org.ecom.backend.orders.application.commands.createoderusecase.dtos;

import org.ecom.backend.orders.domain.OrderItem;

public record CreateOderRequest(String userId, OrderItem item) {

}