package org.ecom.backend.products.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.products.application.ProductCommandService;
import org.ecom.backend.products.application.GetProductPreview;
import org.ecom.backend.products.application.ProductQueryService;
import org.ecom.backend.domain.aggregates.Product;
import org.ecom.backend.products.domain.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductCommandService productService;
    private final ProductQueryService productQueryService;

    @PostMapping("/all")
    @Transactional
    public void saveAll() {

    }

    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public GetProductPreview findById(@PathVariable Long id) {
        return productQueryService.getProduct(id);
    }

    @GetMapping()
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
       return productQueryService.findAll(pageable);
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
