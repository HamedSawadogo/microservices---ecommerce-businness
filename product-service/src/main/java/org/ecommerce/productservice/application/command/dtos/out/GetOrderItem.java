package org.ecommerce.productservice.application.command.dtos.out;


public interface GetOrderItem {
    Long  getId();
    Integer getQuantity();
    GetProductPreview getProduct();
}
