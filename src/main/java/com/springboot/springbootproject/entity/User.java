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

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(nullable = true)
    String firstName;

    @Column(nullable = true)
    String lastName;

    @Column(name = "dob", nullable = true)
    LocalDate dob;

    @ManyToMany
    Set<Role> roles;
}
