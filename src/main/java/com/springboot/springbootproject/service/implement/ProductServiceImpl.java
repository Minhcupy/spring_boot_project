package com.springboot.springbootproject.service.implement;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(String name, Long categoryId, Integer quantity,
                                         BigDecimal price, String description, MultipartFile image) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        String imageUrl = saveImage(image);

        Product product = Product.builder()
                .name(name)
                .category(category)
                .quantity(quantity)
                .price(price)
                .description(description)
                .imageUrl(imageUrl)
                .build();

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, String name, Long categoryId, Integer quantity,
                                         BigDecimal price, String description, MultipartFile image) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
            product.setCategory(category);
        }

        if (name != null) product.setName(name);
        if (quantity != null) product.setQuantity(quantity);
        if (price != null) product.setPrice(price);
        if (description != null) product.setDescription(description);

        if (image != null && !image.isEmpty()) {
            product.setImageUrl(saveImage(image));
        }

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        return productMapper.toProductResponse(
                productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY))
        );
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable).map(productMapper::toProductResponse);
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
