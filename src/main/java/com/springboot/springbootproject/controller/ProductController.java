package com.springboot.springbootproject.controller;

import java.math.BigDecimal;
import java.util.List;

import com.springboot.springbootproject.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.springbootproject.dto.response.ApiResponse;
import com.springboot.springbootproject.dto.response.ProductResponse;
import com.springboot.springbootproject.service.implement.ProductServiceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    // ✅ Thêm mới sản phẩm (multipart/form-data)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> create(
            @RequestParam String name,
            @RequestParam Long categoryId,
            @RequestParam Integer quantity,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, name = "image") MultipartFile image) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(name, categoryId, quantity, price, description, image))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAll(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.getAllProducts(pageable))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProduct(id))
                .build();
    }

    // ✅ Update sản phẩm (có thể sửa cả ảnh)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam Long categoryId,
            @RequestParam Integer quantity,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, name = "image") MultipartFile image) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, name, categoryId, quantity, price, description, image))
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
