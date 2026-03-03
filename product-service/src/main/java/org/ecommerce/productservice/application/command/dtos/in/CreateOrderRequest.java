package org.ecommerce.productservice.application.command.dtos.in;

import java.util.List;

public record CreateOrderRequest(String userId, List<CreateOrderItemRequest> items) {
}
