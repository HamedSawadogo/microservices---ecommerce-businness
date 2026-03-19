package org.ecom.backend.products.application.queries;

import java.math.BigDecimal;

public interface GetProductPreview {
    Long getId();
    String getName();
    BigDecimal getPrice();
}
