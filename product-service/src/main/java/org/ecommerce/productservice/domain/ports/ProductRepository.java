package org.ecommerce.productservice.domain.ports;

import jakarta.persistence.LockModeType;
import org.ecommerce.productservice.application.queries.GetProductPreview;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p.id from Product p")
    Page<Long> fetchProductsIdsPageable(Pageable pageable);

    @Query("select p.id as id , p.name as name, p.price as price  from Product  p where p.id=:id")
    Optional<GetProductPreview> findOne(Long id);

    @Query("SELECT p FROM Product p WHERE p.id IN(:ids)")
    @EntityGraph(attributePaths = {"images", "tags", "category"})
    List<Product> findAll(List<Long> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id=:id")
    Optional<Product> findOneForUpdate(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Product p set p.status=:status where p.id=:id")
    void updateStatus(@Param("id") Long id, @Param("status") ProductStatus status);

    @Query("""
        select p 
        from Product p 
        where lower(p.name) like concat('%', :name, '%')
    """)
    @EntityGraph(attributePaths = {"images", "tags", "category"})
    List<Product> searchByName(String name);
}
