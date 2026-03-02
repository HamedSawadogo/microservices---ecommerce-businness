package org.ecommerce.productservice.application.repositories;

import jakarta.persistence.LockModeType;
import org.ecommerce.productservice.domain.entities.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.ScopedValue;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p.id from Product p")
    Page<Long> fetchProductsIdsPageable(Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN fetch p.tags LEFT JOIN FETCH p.images WHERE p.id IN(:ids)")
    //@QueryHints(value = @QueryHint(name = HINT_CACHEABLE, value = "true"))
    List<Product> findAll(List<Long> ids);

    @Query("SELECT DISTINCT p FROM Product  p LEFT JOIN FETCH  p.tags WHERE p.id=:id")
    List<Product> findWithTags(@Param("id") Long id);

    // @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Modifying
    @Query("UPDATE Product p SET p.status=:status WHERE p.id=:id")
    void updateProductStatus(@Param("status") String status, @Param("id") Long id);


    @Query("UPDATE Product p SET p.price=:price  WHERE p.id=:id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void updatePrice(@Param("price") BigDecimal price, @Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id=:id")
    Optional<Product> findOneForUpdate(Long id);
}
