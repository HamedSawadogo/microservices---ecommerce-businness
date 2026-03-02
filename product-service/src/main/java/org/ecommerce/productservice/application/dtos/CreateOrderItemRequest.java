package org.ecommerce.productservice.application.dtos;

public record CreateOrderItemRequest(Long productId, Integer quantity) {

}