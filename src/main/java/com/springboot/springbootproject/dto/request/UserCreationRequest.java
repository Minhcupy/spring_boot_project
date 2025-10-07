package com.springboot.springbootproject.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.springboot.springbootproject.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    @NotBlank(message = "Username is required")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    @NotBlank(message = "Password is required")
    String password;

    @Size(max = 100, message = "First name must be less than 100 characters")
    String firstName;

    @Size(max = 100, message = "Last name must be less than 100 characters")
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
}
