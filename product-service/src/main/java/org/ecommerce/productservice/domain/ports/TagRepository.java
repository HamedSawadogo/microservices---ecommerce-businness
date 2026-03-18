package org.ecommerce.productservice.domain.ports;


import org.ecommerce.productservice.domain.entities.Tag;
import java.util.Optional;

public interface TagRepository  {
    Optional<Tag> findById(long l);
}
