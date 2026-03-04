package org.ecommerce.productservice.application.queries;

public interface OrderItemResponse {
    Long  getId();
    Integer getQuantity();
    GetProductPreview getProduct();
}