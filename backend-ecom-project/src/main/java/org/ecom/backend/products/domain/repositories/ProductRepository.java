package org.ecom.backend.products.domain.repositories;

import jakarta.persistence.LockModeType;
import org.ecom.backend.products.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p.id from Product p")
    Page<Long> fetchProductsIdsPageable(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id IN(:ids)")
    @EntityGraph(attributePaths = {"images", "tags", "category"})
    List<Product> findAll(List<Long> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id=:id")
    Optional<Product> findOneForUpdate(Long id);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.isDeleted = false")
    Optional<Product> findOneById(@Param("id") Long id);

    @Query("""
        select p 
        from Product p 
        where lower(p.name) like concat('%', :name, '%')
    """)
    @EntityGraph(attributePaths = {"images", "tags", "category"})
    List<Product> searchByName(String name);


}
