package com.springboot.springbootproject.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.springbootproject.dto.response.CategoryResponse;

public interface CategoryRepositoryCustom {

    Long existsByName(String name);

    Optional<CategoryResponse> findByIdCustom(Long id);

    Page<CategoryResponse> findAllCustom(Pageable pageable);
}
