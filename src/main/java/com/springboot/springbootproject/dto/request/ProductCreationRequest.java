package com.springboot.springbootproject.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    Integer quantity;

    @NotNull(message = "PRICE_REQUIRED")
    BigDecimal price;

    String description;

    // ⚡ Thêm ảnh sản phẩm
    @NotBlank(message = "PRODUCT_IMAGE_REQUIRED")
    String imageUrl;
}
