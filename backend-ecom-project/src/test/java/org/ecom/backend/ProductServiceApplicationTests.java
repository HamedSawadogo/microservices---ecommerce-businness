package org.ecom.backend;

import org.ecom.backend.domain.aggregates.Product;
import org.ecom.backend.domain.entities.Tag;
import org.ecom.backend.products.domain.ProductStatus;
import org.ecom.backend.products.domain.ProductRepository;
import org.ecom.backend.products.domain.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceApplicationTests {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void testGetProducts() {

        Product product = new Product();
        product.setStatus(ProductStatus.DRAFT);
        product.getTags().add(new Tag("ELECTRONIQUE", "produits electroniques"));
        productRepository.save(product);


    }

    @Test
    @Transactional
    void removeProductTag() {
        Tag tag = tagRepository.findById(1L).orElseThrow();
        Product product = productRepository.findById(1174852L).orElseThrow();

        System.err.println("  " + product.getTags());
        product.getTags().remove(tag);
        System.err.println(product.getTags());
    }

    @Test
    @Transactional
    void deleteProduct() {
        Product product = productRepository.findById(1174852L).orElseThrow();
        System.err.println("Product" + product);
        productRepository.deleteProductById(product.getId());

        System.err.println( product.isDeleted());
        assertThat(product.isDeleted()).isTrue();
    }

}
