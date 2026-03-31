// ProductMapper.java
package org.ecom.backend.products.application.queries;

import org.ecom.backend.products.application.queries.dtos.ProductDetailsDTO;
import org.ecom.backend.products.application.queries.dtos.ProductPreviewDTO;
import org.ecom.backend.products.domain.entities.Image;
import org.ecom.backend.products.domain.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductPreviewDTO toPreviewDTO(Product product) {
        return new ProductPreviewDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    public ProductDetailsDTO toDetailsDTO(Product product) {
        return new ProductDetailsDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getCreatedAt(),
                product.getImages().stream().map(Image::getImageUrl).toList()
        );
    }
}