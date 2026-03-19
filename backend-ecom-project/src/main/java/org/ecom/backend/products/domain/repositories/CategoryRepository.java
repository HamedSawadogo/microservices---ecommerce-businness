package org.ecom.backend.products.domain.repositories;

import org.ecom.backend.products.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
