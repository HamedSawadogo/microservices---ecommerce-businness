package org.ecommerce.productservice.application.commands;

public record CreateOrderItemRequest(Long productId, Integer quantity) {

}