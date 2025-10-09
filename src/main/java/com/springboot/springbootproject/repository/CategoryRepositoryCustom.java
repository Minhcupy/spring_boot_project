package com.springboot.springbootproject.repository;

import com.springboot.springbootproject.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {

    Long existsByName(String name);

    Optional<CategoryResponse> findByIdCustom(Long id);

    Page<CategoryResponse> findAllCustom(Pageable pageable);
}
