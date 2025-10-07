package com.springboot.springbootproject.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.springboot.springbootproject.validator.DobConstraint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

    @Size(max = 100, message = "First name must be less than 100 characters")
    String firstName;

    @Size(max = 100, message = "Last name must be less than 100 characters")
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    @NotEmpty(message = "User must have at least one role")
    List<String> roles;
}
