package org.ecommerce.productservice.infrastructure.in.rest;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.commands.ProductCommandService;
import org.ecommerce.productservice.application.queries.ProductQueryService;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductCommandService productService;
    private final ProductQueryService productQueryService;

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

    @PutMapping("/{id}")
    public ResponseEntity<ProductStatus> changeProductStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(productService.updateStatus(id, ProductStatus.valueOf(status)));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> search(@RequestParam String name) {
        return ResponseEntity.ok(productQueryService.searchProduct(name));
    }
}
