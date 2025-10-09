package com.springboot.springbootproject.entity;

import java.time.LocalDate;
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
        name = "UserResponseMapping",
        classes =
                @ConstructorResult(
                        targetClass = com.springboot.springbootproject.dto.response.UserResponse.class,
                        columns = {
                            @ColumnResult(name = "id", type = String.class),
                            @ColumnResult(name = "username", type = String.class),
                            @ColumnResult(name = "first_name", type = String.class),
                            @ColumnResult(name = "last_name", type = String.class),
                            @ColumnResult(name = "dob", type = java.time.LocalDate.class),
                            @ColumnResult(name = "role_name", type = String.class)
                        }))
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
