package org.ecommerce.productservice.domain.aggregates;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.productservice.domain.entities.Category;
import org.ecommerce.productservice.domain.entities.Image;
import org.ecommerce.productservice.domain.entities.Tag;
import org.ecommerce.productservice.domain.events.Event;
import org.ecommerce.productservice.domain.events.ProductStatusUpdated;
import org.ecommerce.productservice.domain.exceptions.BussinessException;
import org.ecommerce.productservice.domain.enums.ProductStatus;
import org.ecommerce.productservice.domain.valueobjects.Money;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import java.time.LocalDate;
import java.util.*;

@Entity
@NoArgsConstructor()
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"images", "category", "tags"})
@Table(indexes = {@Index(name = "idx_name", columnList = "name")})
@SQLRestriction("is_deleted = false")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produt_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq")
    private Long id;

    private String name;

    @Embedded
    private Money price;

    private Integer availableQuantity = 0;

    private String description;

    @Transient
    private List<Event> domainEvents = new ArrayList<>();

    @Column(name = "is_deleted")
    private boolean isDeleted;

    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Product(String name, Money price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdAt = LocalDate.now();
        this.status = ProductStatus.DRAFT;
    }

    @AfterDomainEventPublication
    public void clearEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents
    public List<Event> getDomainEvents() {
        return Collections.unmodifiableList(this.domainEvents);
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

    public void updataStatus(ProductStatus status) {
        if (Objects.equals(this.status, status)) {
            return;
        }
        this.status = status;
        this.domainEvents.add(new ProductStatusUpdated(this, this));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return Objects.equals(name, product.name) && Objects.equals(category, product.category) && Objects.equals(tags, product.tags) && Objects.equals(images, product.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, tags, images);
    }
}
