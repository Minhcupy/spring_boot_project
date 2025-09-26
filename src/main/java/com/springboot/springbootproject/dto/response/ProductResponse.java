package com.springboot.springbootproject.dto.response;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    String categoryName;
    Integer quantity;
    BigDecimal price;
    String description;
    String imageUrl; // đường dẫn ảnh (URL hoặc path lưu trong server)
}
