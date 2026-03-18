package org.ecommerce.productservice.domain.ports;


import jakarta.persistence.LockModeType;
import org.ecommerce.productservice.application.queries.GetProductPreview;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Page<Long> fetchProductsIdsPageable(Pageable pageable);
    Optional<GetProductPreview> findOne(Long id);
    List<Product> findAll(List<Long> ids);
    Optional<Product> findOneForUpdate(Long id);
    void updateStatus(Long id, ProductStatus status);
    List<Product> searchByName(String name);
    void deleteProductById(Long id);
    Product save(Product product);
    Optional<Product> findById(Long id);
}
