package org.ecom.backend.products.domain.repositories;


import org.ecom.backend.products.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<org.ecom.backend.products.domain.entities.Tag, Long> {
    Optional<Tag> findById(long l);
}
