package org.ecom.backend.products.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING_FOR_PAYMENTS,
    ORDERED_SUCCESS_FULLY,
    REJECTED
}
