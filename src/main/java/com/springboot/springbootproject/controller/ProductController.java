package com.springboot.springbootproject.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.springboot.springbootproject.dto.request.ProductCreationRequest;
import com.springboot.springbootproject.dto.request.ProductUpdateRequest;
import com.springboot.springbootproject.dto.response.ApiResponse;
import com.springboot.springbootproject.dto.response.PagedResponse;
import com.springboot.springbootproject.dto.response.ProductResponse;
import com.springboot.springbootproject.service.ProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> create(@Valid @ModelAttribute ProductCreationRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PagedResponse<ProductResponse>> getAll(Pageable pageable) {
        var page = productService.getAllProducts(pageable);
        return ApiResponse.<PagedResponse<ProductResponse>>builder()
                .result(PagedResponse.fromPage(page))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProduct(id))
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id, @Valid @ModelAttribute ProductUpdateRequest request) {

        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<String>builder().result("Product deleted").build();
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> search(@RequestParam String keyword) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.searchProducts(keyword))
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<?> getProductsByCategory(@PathVariable Long categoryId, Pageable pageable) {
        Page<ProductResponse> products = productService.getProductsByCategory(categoryId, pageable);
        return ApiResponse.<Page<ProductResponse>>builder().result(products).build();
    }
}
