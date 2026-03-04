package org.ecommerce.productservice.application.commands.orders;

import java.util.List;

public record CreateOrderRequest(String userId, List<CreateOrderItemRequest> items) {
}
