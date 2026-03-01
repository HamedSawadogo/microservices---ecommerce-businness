package org.ecommerce.productservice.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor()
@AllArgsConstructor
@Getter
@ToString(exclude = {"images", "tags", "category"})
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private String description;
    private LocalDate createdAt;
    private String status;

    public Product(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdAt = LocalDate.now();
        this.status = "CREATED";
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();
}
