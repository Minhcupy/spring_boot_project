package com.springboot.springbootproject.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
    private int min;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate value, jakarta.validation.ConstraintValidatorContext context) {
        // Check if the date of birth is null or not
        if (Objects.isNull(value)) return true; // Null values are handled by @NotNull or similar annotations

        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= min; // Check if the age is greater than or equal to the minimum age
    }
}
