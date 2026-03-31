// ProductPageDTO.java — pagination sérialisable
package org.ecom.backend.products.application.queries.dtos;

import java.io.Serializable;
import java.util.List;

public record ProductPageDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) implements Serializable {

    public static <T> ProductPageDTO<T> of(List<T> content, int pageNumber,
                                            int pageSize, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        return new ProductPageDTO<>(content, pageNumber, pageSize, totalElements, totalPages);
    }
}