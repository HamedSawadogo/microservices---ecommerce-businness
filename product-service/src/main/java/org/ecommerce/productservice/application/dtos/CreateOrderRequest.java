package org.ecommerce.productservice.application.dtos;

import java.util.List;

public record CreateOrderRequest(String userId, List<CreateOrderItemRequest> items) {
}
