package org.ecommerce.productservice.application.commands.orders;

public record CreateOrderItemRequest(Long productId, Integer quantity) {

}