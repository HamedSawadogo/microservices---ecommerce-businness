package org.ecom.backend.products.application.comands;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.products.domain.enums.ProductStatus;
import org.ecom.backend.products.domain.events.ProductCreated;
import org.ecom.backend.products.domain.events.ProductStatusUpdated;
import org.ecom.backend.products.domain.repositories.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
  private final ProductRepository productRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public ProductStatus updateStatus(Long id, ProductStatus productStatus) {
     var product = productRepository.findById(id).orElseThrow();
     product.updataStatus(productStatus);
     applicationEventPublisher.publishEvent(new ProductStatusUpdated(this, product));
     return productStatus;
  }


  @Transactional
  public Product create(Product product) {
     var productSaved  =   productRepository.save(product);
     applicationEventPublisher.publishEvent(new ProductCreated(this, product));
     return productSaved;
  }
}
