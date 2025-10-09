package com.springboot.springbootproject.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SqlResultSetMapping(
        name = "CategoryResponseMapping",
        classes =
                @ConstructorResult(
                        targetClass = com.springboot.springbootproject.dto.response.CategoryResponse.class,
                        columns = {
                            @ColumnResult(name = "id", type = Long.class),
                            @ColumnResult(name = "name", type = String.class),
                            @ColumnResult(name = "description", type = String.class),
                            @ColumnResult(name = "product_count", type = Long.class)
                        }))
@Table(
        name = "categories",
        indexes = {@Index(name = "idx_category_name", columnList = "name")})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 100)
    String name;

    @Column(name = "description", length = 200)
    String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Product> products;
}
