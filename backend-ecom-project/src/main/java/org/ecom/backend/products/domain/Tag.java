package org.ecom.backend.products.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Tag {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;

  public Tag(String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Tag tag)) return false;
      return Objects.equals(name, tag.name) && Objects.equals(description, tag.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }
}
