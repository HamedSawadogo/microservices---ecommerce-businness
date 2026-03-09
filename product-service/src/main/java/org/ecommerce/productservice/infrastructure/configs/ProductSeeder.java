package org.ecommerce.productservice.infrastructure.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.entities.Category;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.ecommerce.productservice.domain.ports.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductSeeder {

    @PersistenceContext
    private EntityManager entityManager;

    private final CategoryRepository categoryRepository;

    private static final int BATCH_SIZE = 50;

    @Transactional  // ✅ @Transactional ici a un effet réel
    public void seed() {
        // ✅ récupéré une seule fois avant la boucle
        Category category = categoryRepository.findById(1L).orElse(null);

        IntStream.range(1, 1000_000).forEach(val -> {
            Product product = new Product();
            product.setCategory(category);
            product.setName("Produit: " + val);
            product.setPrice(BigDecimal.valueOf(val * 200d));
            product.setDescription("Description: " + val);
            product.setAvailableQuantity(10);
            product.setStatus(ProductStatus.PUBLISHED);
            entityManager.persist(product);

            if (val % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear(); // ✅ libère le cache 1er niveau
            }
        });
    }
}