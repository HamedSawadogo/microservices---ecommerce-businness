package org.ecommerce.productservice;

import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.entities.Tag;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.ecommerce.productservice.domain.ports.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
