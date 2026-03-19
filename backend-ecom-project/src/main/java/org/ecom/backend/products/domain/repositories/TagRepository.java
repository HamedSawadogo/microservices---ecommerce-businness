package org.ecom.backend.products.domain.repositories;


import org.ecom.backend.domain.entities.Tag;
import java.util.Optional;

public interface TagRepository  {
    Optional<Tag> findById(long l);
}
