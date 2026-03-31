package org.ecom.backend.products.web;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.products.application.comands.ProductCommandService;
import org.ecom.backend.products.application.queries.ProductQueryService;
import org.ecom.backend.products.application.queries.dtos.ProductDetailsDTO;
import org.ecom.backend.products.application.queries.dtos.ProductPageDTO;
import org.ecom.backend.products.application.queries.dtos.ProductPreviewDTO;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.products.domain.enums.ProductStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductCommandService productService;
    private final ProductQueryService productQueryService;

    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ProductDetailsDTO findById(@PathVariable Long id) {
        return productQueryService.getProduct(id);
    }

    @GetMapping()
    @Transactional(readOnly = true)
    public  ProductPageDTO<ProductPreviewDTO>  findAll(Pageable pageable) {
       return productQueryService.findAll(pageable);
    }

    @PostMapping("/import/smartphones")
    public String importProducts() {
        productService.importSmartphones();
        return "Import started";
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductStatus> changeProductStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(productService.updateStatus(id, ProductStatus.valueOf(status)));
    }

    @GetMapping("/search")
    public ResponseEntity<ProductPageDTO<ProductPreviewDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(productQueryService.searchProduct(name));
    }
}
