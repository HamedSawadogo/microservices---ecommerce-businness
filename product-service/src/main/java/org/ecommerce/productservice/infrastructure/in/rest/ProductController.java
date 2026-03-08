package org.ecommerce.productservice.infrastructure.in.rest;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.commands.products.ProductService;
import org.ecommerce.productservice.domain.entities.products.Product;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping()
    public Product createProduct(@RequestBody Product product) throws Exception {
        return productService.create(product);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public Product findById(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @GetMapping()
    @Transactional(readOnly = true)
    public Page<Product> findAllWithTagsAndCategory(Pageable pageable) {
        var result = productRepository.fetchProductsIdsPageable(pageable);
        var ids = result.getContent();
        var products = productRepository.findAll(ids);
        return new PageImpl<>(products, pageable, result.getTotalElements());
    }
}
