package org.ecommerce.productservice.application.commands;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.events.ProductCreated;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public Product create(Product product) throws Exception {
        Product created  = new Product(product.getName(), product.getPrice(), product.getDescription());
        applicationEventPublisher.publishEvent(new ProductCreated(this, created));
        productRepository.save(product);
        if (true) {
            throw new Exception("error");
        }
        return product;
    }
}
