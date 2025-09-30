package com.springboot.springbootproject.service;

import com.springboot.springbootproject.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(
            String name,
            Long categoryId,
            Integer quantity,
            BigDecimal price,
            String description,
            MultipartFile image
    );

    ProductResponse updateProduct(
            Long id,
            String name,
            Long categoryId,
            Integer quantity,
            BigDecimal price,
            String description,
            MultipartFile image
    );

    void deleteProduct(Long id);

    ProductResponse getProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable);
}
