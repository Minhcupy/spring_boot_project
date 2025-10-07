package com.springboot.springbootproject.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {

    @NotBlank(message = "Role name is required")
    String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    String description;
    Set<String> permissions;
}
