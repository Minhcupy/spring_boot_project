package com.springboot.springbootproject.entity;

import java.util.Date;

import jakarta.persistence.*;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class InvalidatedToken {
    @Id
    @NotBlank(message = "Token ID is required")
    @Column(nullable = false, unique = true, length = 500)
    String id;

    @NotNull(message = "Expiry time is required")
    @Future(message = "Expiry time must be in the future")
    @Temporal(TemporalType.TIMESTAMP)
    Date expiryTime;
}
