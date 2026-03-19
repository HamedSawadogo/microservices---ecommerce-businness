package org.ecom.backend.products.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    //TODO to fix that code
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return "Category{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
