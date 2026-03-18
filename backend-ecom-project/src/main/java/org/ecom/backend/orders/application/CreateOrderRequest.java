package org.ecom.backend.orders.application;

import java.util.List;

public record CreateOrderRequest(String userId, List<CreateOrderItemRequest> items) {
}
