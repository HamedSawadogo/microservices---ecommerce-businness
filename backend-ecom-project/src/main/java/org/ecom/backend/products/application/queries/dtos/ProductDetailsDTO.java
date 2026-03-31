package org.ecom.backend.products.application.queries.dtos;

import org.ecom.backend.shared.valueobjects.Money;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ProductDetailsDTO(
        Long id,
        String name,
        Money price,
        String description,
        Integer stock,
        LocalDate createdAt,
        List<String> imageUrls
) implements Serializable {}