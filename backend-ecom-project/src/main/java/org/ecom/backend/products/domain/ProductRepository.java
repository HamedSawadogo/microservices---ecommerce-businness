package org.ecom.backend.products.domain;


import org.ecom.backend.products.application.GetProductPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Page<Long> fetchProductsIdsPageable(Pageable pageable);
    Optional<GetProductPreview> findOne(Long id);
    List<Product> findAll(List<Long> ids);
    Optional<Product> findOneForUpdate(Long id);
    List<Product> searchByName(String name);
    Product save(Product product);
    Optional<Product> findById(Long id);

    void deleteProductById(Long id);
    void updateStatus(Long id, ProductStatus status);

}
