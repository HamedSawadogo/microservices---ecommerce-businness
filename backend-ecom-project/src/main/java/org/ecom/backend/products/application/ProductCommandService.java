package org.ecom.backend.products.application;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.products.domain.ProductStatus;
import org.ecom.backend.products.domain.ProductCreated;
import org.ecom.backend.products.domain.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
  private final ProductRepository productRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Product create(Product product) {
        Product created  = new Product(product.getName(), product.getPrice(), product.getDescription());
        applicationEventPublisher.publishEvent(new ProductCreated(this, created));
        productRepository.save(product);
        return product;
    }

    @Transactional
    public ProductStatus  updateStatus(Long id, ProductStatus status) {
        var product = productRepository.findById(id).orElseThrow();
        product.updataStatus(status);
        productRepository.save(product);
        return product.getStatus();
    }
}
