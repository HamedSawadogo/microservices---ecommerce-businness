package org.ecommerce.productservice.infrastructure.out.persistence.adapters;

import org.ecommerce.productservice.application.queries.GetProductPreview;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Page<Long> fetchProductsIdsPageable(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<GetProductPreview> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll(List<Long> ids) {
        return List.of();
    }

    @Override
    public Optional<Product> findOneForUpdate(Long id) {
        return Optional.empty();
    }

    @Override
    public void updateStatus(Long id, ProductStatus status) {

    }

    @Override
    public List<Product> searchByName(String name) {
        return List.of();
    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }
}
