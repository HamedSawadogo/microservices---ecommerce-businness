package org.ecommerce.productservice.application.commands.products;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.repositories.ProductRepository;
import org.ecommerce.productservice.domain.entities.products.Product;
import org.ecommerce.productservice.domain.events.ProductCreated;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional(rollbackFor = IllegalArgumentException.class)
  public Product create(Product product) {
      Product created  = new Product(product.getName(), product.getPrice(), product.getDescription());
      applicationEventPublisher.publishEvent(new ProductCreated(this, created));
      return productRepository.save(product);
  }
}
