package org.ecommerce.productservice;

import org.ecommerce.productservice.application.repositories.ProductRepository;
import org.ecommerce.productservice.domain.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void testGetProducts() {

    }

}
