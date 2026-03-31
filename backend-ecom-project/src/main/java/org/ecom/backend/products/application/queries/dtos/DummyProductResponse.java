package org.ecom.backend.products.application.queries.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DummyProductResponse {
    private List<DummyProduct> products;
}