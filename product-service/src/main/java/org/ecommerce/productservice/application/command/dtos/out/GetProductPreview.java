package org.ecommerce.productservice.application.command.dtos.out;

import java.math.BigDecimal;

public interface GetProductPreview {
    Long getId();
    String getName();
    BigDecimal getPrice();
}
