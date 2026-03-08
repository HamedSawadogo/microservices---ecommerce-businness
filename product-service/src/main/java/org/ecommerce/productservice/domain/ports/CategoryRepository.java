package org.ecommerce.productservice.domain.ports;

import org.ecommerce.productservice.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
