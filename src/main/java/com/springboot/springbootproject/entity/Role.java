package com.springboot.springbootproject.entity;

import java.util.Set;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
        name = "RoleResponseMapping",
        classes = @ConstructorResult(
                targetClass = com.springboot.springbootproject.dto.response.RoleResponse.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "description", type = String.class)
                }
        )
)
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "name")
    String name;

    @Column(name = "description", length = 200)
    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Permission> permissions;
}
