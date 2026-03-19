package org.ecom.backend.orders.application.commands.createoderusecase.dtos;

import java.util.List;

public record CreateOrderRequest(String userId, List<CreateOrderItemRequest> items) {
}
