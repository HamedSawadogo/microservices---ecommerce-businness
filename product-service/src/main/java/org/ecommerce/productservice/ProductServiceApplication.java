package org.ecommerce.productservice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.ecommerce.productservice.domain.ports.CategoryRepository;
import org.ecommerce.productservice.domain.ports.OrderRepository;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.ecommerce.productservice.infrastructure.configs.ProductSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.IntStream;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner start(ProductSeeder seeder) {
      return args -> {
      };
    }
}
