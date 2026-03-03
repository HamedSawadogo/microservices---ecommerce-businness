package org.ecommerce.productservice.application.command.dtos.in;

public record CreateOrderItemRequest(Long productId, Integer quantity) {

}