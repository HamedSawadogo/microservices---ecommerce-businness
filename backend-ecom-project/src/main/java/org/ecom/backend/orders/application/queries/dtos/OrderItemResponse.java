package org.ecom.backend.orders.application.queries.dtos;

import org.ecom.backend.products.application.queries.GetProductPreview;

public interface OrderItemResponse {
    Long  getId();
    Integer getQuantity();
    GetProductPreview getProduct();
}