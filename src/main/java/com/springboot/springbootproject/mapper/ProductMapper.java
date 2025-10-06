package com.springboot.springbootproject.mapper;

import org.mapstruct.*;

import com.springboot.springbootproject.dto.request.ProductCreationRequest;
import com.springboot.springbootproject.dto.request.ProductUpdateRequest;
import com.springboot.springbootproject.dto.response.ProductResponse;
import com.springboot.springbootproject.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreationRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
