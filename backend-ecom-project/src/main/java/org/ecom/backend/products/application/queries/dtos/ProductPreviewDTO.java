package org.ecom.backend.products.application.queries.dtos;

import org.ecom.backend.shared.valueobjects.Money;
import java.io.Serializable;

public record ProductPreviewDTO(
        Long id,
        String name,
        Money price
) implements Serializable {}