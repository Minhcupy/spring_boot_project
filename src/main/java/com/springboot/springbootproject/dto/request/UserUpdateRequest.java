package com.springboot.springbootproject.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.springboot.springbootproject.validator.DobConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;

//    @NotBlank(message = "EMAIL_REQUIRED")
//    @Email(message = "INVALID_EMAIL_FORMAT")
//    String email;

    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
