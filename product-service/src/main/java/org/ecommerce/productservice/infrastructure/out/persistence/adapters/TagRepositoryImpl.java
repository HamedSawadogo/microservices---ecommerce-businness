package org.ecommerce.productservice.infrastructure.out.persistence.adapters;

import org.ecommerce.productservice.domain.entities.Tag;
import org.ecommerce.productservice.domain.ports.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @Override
    public Optional<Tag> findById(long l) {
        return Optional.empty();
    }
}
