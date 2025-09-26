package com.springboot.springbootproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.springboot.springbootproject.dto.request.CategoryRequest;
import com.springboot.springbootproject.dto.response.CategoryResponse;
import com.springboot.springbootproject.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
