package com.springboot.springbootproject.entity;

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
        name = "PermissionResponseMapping",
        classes = @ConstructorResult(
                targetClass = com.springboot.springbootproject.dto.response.PermissionResponse.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "description", type = String.class)
                }
        )
)
public class Permission {
    @Id
    @Column(unique = true, nullable = false, length = 100)
    String name;

    @Column(name = "description", length = 200)
    String description;
}
