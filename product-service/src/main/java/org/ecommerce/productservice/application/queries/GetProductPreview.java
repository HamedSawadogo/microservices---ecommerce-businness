package org.ecommerce.productservice.application.queries;

import java.math.BigDecimal;

public interface GetProductPreview {
    Long getId();
    String getName();
    BigDecimal getPrice();
}
