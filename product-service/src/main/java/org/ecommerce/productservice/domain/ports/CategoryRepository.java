package org.ecommerce.productservice.domain.repositories;

import org.ecommerce.productservice.domain.entities.products.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
