package org.ecommerce.productservice;

import org.ecommerce.productservice.application.repositories.CategoryRepository;
import org.ecommerce.productservice.application.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository,
                            CategoryRepository categoryRepository) {
        return args ->  {
//           productRepository.findAll().forEach(product -> {
//               Tag tag = new Tag();
//               tag.setDescription("Java");
//               Tag tag2 = new Tag();
//               tag.setDescription("C++");
//               product.getTags().add(tag2);
//               product.getTags().add(tag);
//               productRepository.save(product);
//           });
        };
    }

}
