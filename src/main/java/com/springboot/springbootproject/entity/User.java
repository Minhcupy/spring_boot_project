package com.springboot.springbootproject.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

    @Column(nullable = true)
    @Size(max = 100, message = "First name must be less than 100 characters")
    String firstName;

    @Column(nullable = true)
    @Size(max = 100, message = "Last name must be less than 100 characters")
    String lastName;

    @Past(message = "Date of birth must be in the past")
    LocalDate dob;

    @NotEmpty(message = "User must have at least one role")
    @ManyToMany
    Set<Role> roles;
}
