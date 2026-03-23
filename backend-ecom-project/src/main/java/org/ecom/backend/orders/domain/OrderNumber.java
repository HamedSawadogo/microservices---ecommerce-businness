package org.ecom.backend.orders.domain;

import jakarta.persistence.Embeddable;
import org.ecom.backend.shared.exceptions.BussinessException;
import java.util.Objects;

@Embeddable
public record OrderNumber(String number) {
    public OrderNumber {
        if (Objects.isNull(number)) {
            throw new BussinessException("Invalid Order Number");
        }
    }
}
