package org.ecom.backend.products.application.comands;

import lombok.RequiredArgsConstructor;
import org.ecom.backend.products.application.queries.dtos.DummyProduct;
import org.ecom.backend.products.application.queries.dtos.DummyProductResponse;
import org.ecom.backend.products.domain.entities.Product;
import org.ecom.backend.products.domain.enums.ProductStatus;
import org.ecom.backend.products.domain.events.ProductCreated;
import org.ecom.backend.products.domain.events.ProductStatusUpdated;
import org.ecom.backend.products.domain.repositories.ProductRepository;
import org.ecom.backend.shared.valueobjects.Money;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
  private final ProductRepository productRepository;
  private final RestTemplate restTemplate;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public ProductStatus updateStatus(Long id, ProductStatus productStatus) {
     var product = productRepository.findById(id).orElseThrow();
     product.updataStatus(productStatus);
     applicationEventPublisher.publishEvent(new ProductStatusUpdated(this, product));
     return productStatus;
  }

    @Retryable(
            value = Exception.class
    )
    public void importSmartphones() {
        String url = "https://dummyjso.com/products/category/smartphones";
        DummyProductResponse response = restTemplate.getForObject(url, DummyProductResponse.class);
        if (response == null || response.getProducts() == null) return;
        response.getProducts().forEach(this::saveProduct);
    }

    private void saveProduct(DummyProduct p) {
        Product product = new Product();
        product.setName(p.getTitle());
        product.setDescription(p.getDescription());
        product.setPrice(Money.zero());
        create(product);
    }


  @Transactional
  public Product create(Product product) {
     var productSaved  =   productRepository.save(product);
     applicationEventPublisher.publishEvent(new ProductCreated(this, product));
     return productSaved;
  }
}
