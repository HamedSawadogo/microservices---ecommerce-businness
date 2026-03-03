package org.ecommerce.productservice.application.command.orders;

public record CreateOrderItemRequest(Long productId, Integer quantity) {

}