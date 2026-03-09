package org.ecommerce.productservice.application.queries;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> searchProduct(String name) {
        List<Product> products = productRepository.searchByName(name);
        System.err.println("product  "  + products.size());
        return new PageImpl<>(products);
    }
}
