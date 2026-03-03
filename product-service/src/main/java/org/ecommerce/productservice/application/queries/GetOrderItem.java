package org.ecommerce.productservice.application.queries;


public interface GetOrderItem {
    Long  getId();
    Integer getQuantity();
    GetProductPreview getProduct();
}
