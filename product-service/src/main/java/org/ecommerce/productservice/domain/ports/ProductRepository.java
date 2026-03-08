package org.ecommerce.productservice.domain.ports;

import jakarta.persistence.LockModeType;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p.id from Product p")
    Page<Long> fetchProductsIdsPageable(Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN fetch p.tags LEFT JOIN FETCH p.images WHERE p.id IN(:ids)")
    List<Product> findAll(List<Long> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id=:id")
    Optional<Product> findOneForUpdate(Long id);
}
