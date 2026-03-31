package org.ecom.backend.products.application.queries;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ecom.backend.products.application.queries.dtos.ProductDetailsDTO;
import org.ecom.backend.products.application.queries.dtos.ProductPageDTO;
import org.ecom.backend.products.application.queries.dtos.ProductPreviewDTO;
import org.ecom.backend.products.domain.repositories.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "products-search", key = "#name")
    public ProductPageDTO<ProductPreviewDTO> searchProduct(String name) {
        List<ProductPreviewDTO> content = productRepository
                .searchByName(name.toLowerCase())
                .stream()
                .map(productMapper::toPreviewDTO)
                .toList();

        return ProductPageDTO.of(content, 0, content.size(), content.size());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "product-details", key = "#id")
    public ProductDetailsDTO getProduct(Long id) {
        return productRepository.findOneById(id)
                .map(productMapper::toDetailsDTO)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public ProductPageDTO<ProductPreviewDTO> findAll(Pageable pageable) {
        var result = productRepository.fetchProductsIdsPageable(pageable);
        var ids = result.getContent();

        List<ProductPreviewDTO> content = productRepository.findAll(ids)
                .stream()
                .map(productMapper::toPreviewDTO)
                .toList();

        return ProductPageDTO.of(
                content,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                result.getTotalElements()
        );
    }
}