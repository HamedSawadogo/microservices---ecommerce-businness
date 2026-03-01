package org.ecommerce.productservice.application.controllers;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.repositories.ProductRepository;
import org.ecommerce.productservice.application.services.ProductService;
import org.ecommerce.productservice.domain.entities.Product;
import org.ecommerce.productservice.domain.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products" )
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("/with-tags" )
    public List<Product> getProductWithTags() {
        return productRepository.findWithTags(1L);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public Product findById(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }


    @GetMapping()
    @Transactional(readOnly = true)
    public Page<Product> findAllWithTagsAndCategory(Pageable pageable) {
        System.err.println(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal());
        var result = productRepository.fetchProductsIdsPageable(pageable);
        var ids = result.getContent();
        var products = productRepository.findAll(ids);

        return new PageImpl<>(products, pageable, result.getTotalElements());
    }

    @PutMapping("/{id}")
    public BigDecimal updateProductPrice(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.updatePrice(BigDecimal.valueOf(600000), id);
        return product.getPrice();
    }
}
