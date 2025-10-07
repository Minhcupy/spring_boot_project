package com.springboot.springbootproject.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {

    @NotBlank(message = "PRODUCT_NAME_REQUIRED")
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

    String description;

    // ⚡ Thêm ảnh sản phẩm
    @NotBlank(message = "PRODUCT_IMAGE_REQUIRED")
    String imageUrl;
}
