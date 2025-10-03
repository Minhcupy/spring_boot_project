package com.springboot.springbootproject.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.springboot.springbootproject.dto.request.CategoryRequest;
import com.springboot.springbootproject.dto.response.CategoryResponse;
import com.springboot.springbootproject.entity.Category;
import com.springboot.springbootproject.exception.AppException;
import com.springboot.springbootproject.exception.ErrorCode;
import com.springboot.springbootproject.mapper.CategoryMapper;
import com.springboot.springbootproject.repository.CategoryRepository;
import com.springboot.springbootproject.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        return categoryMapper.toCategoryResponse(
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryResponse> getCategories(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryResponse);
    }
}
