package org.ecommerce.productservice.domain.entities.products;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.productservice.application.BussinessException;
import org.ecommerce.productservice.domain.enums.ProductStatus;
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
    private Integer availableQuantity;
    private String description;
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public Product(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdAt = LocalDate.now();
        this.status = ProductStatus.DRAFT;
    }

    public void decreaseQuantity(Integer quantity) {
        if (quantity > this.availableQuantity) {
            throw new BussinessException("Product has not sufficcent stock");
        }
        this.availableQuantity -= quantity;
    }

    public boolean isAvailableStock() {
        return this.availableQuantity > 0;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();
}
