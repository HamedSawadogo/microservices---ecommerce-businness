package org.ecom.backend.products.application.comands;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.products.domain.enums.ProductStatus;
import org.ecom.backend.products.domain.repositories.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
  private final ProductRepository productRepository;
  private final ApplicationEventPublisher applicationEventPublisher;


  public ProductStatus updateStatus(Long id, ProductStatus productStatus) {
    return null;
  }

  public Product create(Product product) {
      return null;
  }
}
