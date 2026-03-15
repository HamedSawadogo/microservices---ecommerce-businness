package org.ecommerce.productservice.domain.ports;

import org.ecommerce.productservice.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
