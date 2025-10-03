package com.springboot.springbootproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
public class Permission {
    @Id
    @NotBlank(message = "Permission name is required")
    @Size(min = 3, max = 100, message = "Permission name must be between 3 and 100 characters")
    @Column(unique = true, nullable = false, length = 100)
    String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    String description;
}
