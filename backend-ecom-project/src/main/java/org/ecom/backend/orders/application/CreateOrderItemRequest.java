package org.ecom.backend.orders.application;

public record CreateOrderItemRequest(Long productId, Integer quantity) {

}