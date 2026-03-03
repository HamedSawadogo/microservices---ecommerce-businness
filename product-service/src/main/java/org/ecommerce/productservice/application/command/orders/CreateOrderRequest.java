package org.ecommerce.productservice.application.command.orders;

import java.util.List;

public record CreateOrderRequest(String userId, List<CreateOrderItemRequest> items) {
}
