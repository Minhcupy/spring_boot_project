package com.springboot.springbootproject.service.implement;

import java.util.List;

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
import com.springboot.springbootproject.repository.ProductRepository;
import com.springboot.springbootproject.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    CategoryMapper categoryMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(CategoryRequest request) {
        Long count = categoryRepository.existsByName(request.getName());
        if (count != null && count > 0) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.toCategory(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(saved);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category existing = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        Long count = categoryRepository.existsByName(request.getName());
        if (count != null && count > 0 && !existing.getName().equalsIgnoreCase(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        categoryMapper.updateCategory(existing, request);
        Category updated = categoryRepository.save(existing);
        return categoryMapper.toCategoryResponse(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        if (productRepository.existsByCategoryIdCustom(id)) {
            throw new AppException(ErrorCode.CATEGORY_HAS_PRODUCTS);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        return categoryRepository.findByIdCustom(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        // Ở đây ta có thể lấy toàn bộ page đầu tiên đủ size lớn
        Page<CategoryResponse> page = categoryRepository.findAllCustom(PageRequest.of(0, Integer.MAX_VALUE));
        return page.getContent();
    }

    @Override
    public Page<CategoryResponse> getCategories(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return categoryRepository.findAllCustom(pageable);
    }
}
