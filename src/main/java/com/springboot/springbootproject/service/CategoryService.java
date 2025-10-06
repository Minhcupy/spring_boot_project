package com.springboot.springbootproject.service;

import java.util.List;

import com.springboot.springbootproject.dto.request.CategoryRequest;
import com.springboot.springbootproject.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse getCategory(Long id);

    List<CategoryResponse> getAllCategories();

    Page<CategoryResponse> getCategories(int page, int size);
}
