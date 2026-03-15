package org.ecommerce.productservice.application.queries;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> searchProduct(String name) {
        List<Product> products = productRepository.searchByName(name.toLowerCase());
        return new PageImpl<>(products);
    }

    @Transactional(readOnly = true)
    public GetProductPreview getProduct(Long id) {
        return productRepository.findOne(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        var result = productRepository.fetchProductsIdsPageable(pageable);
        var ids = result.getContent();
        var products = productRepository.findAll(ids);
        return new PageImpl<>(products, pageable, result.getTotalElements());
    }
}
