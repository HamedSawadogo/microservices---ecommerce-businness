package org.ecom.backend.products.infrastructure;

import org.ecom.backend.domain.entities.Tag;
import org.ecom.backend.products.domain.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @Override
    public Optional<Tag> findById(long l) {
        return Optional.empty();
    }
}
