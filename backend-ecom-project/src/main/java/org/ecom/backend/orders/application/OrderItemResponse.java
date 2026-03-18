package org.ecom.backend.orders.application;

import org.ecom.backend.products.application.GetProductPreview;

public interface OrderItemResponse {
    Long  getId();
    Integer getQuantity();
    GetProductPreview getProduct();
}