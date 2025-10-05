package com.springboot.springbootproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product p WHERE p.id = :id", nativeQuery = true)
    Optional<Product> findById(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM product p",
            countQuery = "SELECT COUNT(*) FROM product",
            nativeQuery = true
    )
    Page<Product> findAll(Pageable pageable);

    @Query(
            value = "SELECT * FROM product p WHERE p.category_id = :categoryId",
            countQuery = "SELECT COUNT(*) FROM product p WHERE p.category_id = :categoryId",
            nativeQuery = true
    )
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = """
    SELECT * FROM product p 
    WHERE p.name COLLATE utf8mb4_0900_ai_ci 
    LIKE CONCAT('%', :keyword, '%')
    """, nativeQuery = true)
    List<Product> findByNameContainingIgnoreCase(@Param("keyword") String keyword);

}
