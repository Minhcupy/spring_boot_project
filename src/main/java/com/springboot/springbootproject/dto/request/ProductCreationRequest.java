package com.springboot.springbootproject.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {

    @NotBlank(message = "PRODUCT_NAME_REQUIRED")
    @Size(min = 2, max = 5, message = "Product name must be between 2 and 100 characters")
    String name;

    @NotNull(message = "CATEGORY_ID_REQUIRED")
    Long categoryId;

    @NotNull(message = "QUANTITY_REQUIRED")
    @Min(value = 0, message = "Quantity must be >= 0")
    Integer quantity;

    @NotNull(message = "PRICE_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be >= 0")
    @Digits(integer = 12, fraction = 2, message = "Price format is invalid")
    BigDecimal price;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description;

    // ⚡ Thêm ảnh sản phẩm
    MultipartFile image;
}
