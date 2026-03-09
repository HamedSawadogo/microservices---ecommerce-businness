package org.ecommerce.productservice.application.commands;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.ecommerce.productservice.domain.events.ProductCreated;
import org.ecommerce.productservice.domain.ports.CategoryRepository;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public Product create(Product product) {
        Product created  = new Product(product.getName(), product.getPrice(), product.getDescription());
        applicationEventPublisher.publishEvent(new ProductCreated(this, created));
        productRepository.save(product);
        return product;
    }

    @Transactional
    public ProductStatus  updateStatus(Long id, ProductStatus status) {
        productRepository.updateStatus(id, status);
        return status;
    }


}
