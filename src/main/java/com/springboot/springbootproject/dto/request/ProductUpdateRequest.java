package com.springboot.springbootproject.dto.request;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {
    String name;
    Long categoryId;
    Integer quantity;
    BigDecimal price;
    String description;
    String imageUrl; // đường dẫn ảnh (URL hoặc path lưu trong server)
}
