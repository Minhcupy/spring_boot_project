package com.springboot.springbootproject.service;

import java.math.BigDecimal;
import java.util.List;

import com.springboot.springbootproject.dto.request.ProductCreationRequest;
import com.springboot.springbootproject.dto.request.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.springbootproject.dto.response.ProductResponse;

public interface ProductService {
    public ProductResponse createProduct(ProductCreationRequest request);

    public ProductResponse updateProduct(Long id, ProductUpdateRequest request);

    void deleteProduct(Long id);

    ProductResponse getProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable);
}
