package org.ecommerce.productservice.application.queries;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.aggregates.Product;
import org.ecommerce.productservice.domain.ports.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductRepository productRepository;
    private final RedisTemplate redisTemplate;

    @Transactional(readOnly = true)
    public Page<Product> searchProduct(String name) {
        List<Product> products = productRepository.searchByName(name.toLowerCase());
        return new PageImpl<>(products);
    }

    @Transactional(readOnly = true)
    public GetProductPreview getProduct(Long id) {
        var  product = productRepository.findOne(id).orElseThrow();
        return product;
    }

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        var result = productRepository.fetchProductsIdsPageable(pageable);
        var ids = result.getContent();
        var products = productRepository.findAll(ids);
        redisTemplate.opsForValue().set("products", Arrays.toString(products.toArray()));
        System.err.println(redisTemplate.opsForValue().get("products"));
        if (redisTemplate.opsForValue().get("products") != null) {
            System.err.println("EXISTS");
        }
        return new PageImpl<>(products, pageable, result.getTotalElements());
    }
}
