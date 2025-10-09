package com.springboot.springbootproject.service.implement;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.springbootproject.dto.request.ProductCreationRequest;
import com.springboot.springbootproject.dto.request.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.springbootproject.dto.response.ProductResponse;
import com.springboot.springbootproject.entity.Category;
import com.springboot.springbootproject.entity.Product;
import com.springboot.springbootproject.exception.AppException;
import com.springboot.springbootproject.exception.ErrorCode;
import com.springboot.springbootproject.mapper.ProductMapper;
import com.springboot.springbootproject.repository.CategoryRepository;
import com.springboot.springbootproject.repository.ProductRepository;
import com.springboot.springbootproject.service.ProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(ProductCreationRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        String imageUrl = saveImage(request.getImage());

        Product product = Product.builder()
                .name(request.getName())
                .category(category)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .description(request.getDescription())
                .imageUrl(imageUrl)
                .build();

        Product saved = productRepository.save(product);
        return productMapper.toProductResponse(saved);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
            product.setCategory(category);
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            product.setName(request.getName());
        }
        if (request.getQuantity() != null) {
            product.setQuantity(request.getQuantity());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        // ✅ Xử lý file ảnh (nếu có upload mới)
        MultipartFile image = request.getImage();
        if (image != null && !image.isEmpty()) {
            product.setImageUrl(saveImage(image));
        }

        Product updated = productRepository.save(product);
        return productMapper.toProductResponse(updated);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        return productRepository.findByIdCustom(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAllCustom(pageable);
    }

    @Override
    public Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    private String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) return null;

        try {
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = image.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, image.getBytes());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
