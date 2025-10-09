package com.springboot.springbootproject.repository;

import com.springboot.springbootproject.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    Optional<ProductResponse> findByIdCustom(Long id);

    Page<ProductResponse> findAllCustom(Pageable pageable);

    Page<ProductResponse> findByCategoryId(Long categoryId, Pageable pageable);

    List<ProductResponse> findByNameContainingIgnoreCase(String keyword);

    boolean existsByCategoryIdCustom(Long categoryId);
}
