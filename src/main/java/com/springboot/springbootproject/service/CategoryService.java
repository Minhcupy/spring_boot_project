package com.springboot.springbootproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.springbootproject.dto.request.CategoryRequest;
import com.springboot.springbootproject.dto.response.CategoryResponse;
import com.springboot.springbootproject.entity.Category;
import com.springboot.springbootproject.exception.AppException;
import com.springboot.springbootproject.exception.ErrorCode;
import com.springboot.springbootproject.mapper.CategoryMapper;
import com.springboot.springbootproject.repository.CategoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) throw new AppException(ErrorCode.INVALID_KEY);

        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        categoryMapper.updateCategory(category, request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponse getCategory(Long id) {
        return categoryMapper.toCategoryResponse(
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }
}
