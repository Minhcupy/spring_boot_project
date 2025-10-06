package com.springboot.springbootproject.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT COUNT(*) FROM categories WHERE LOWER(name) = LOWER(:name)", nativeQuery = true)
    Long existsByNameNative(@Param("name") String name);

    @Query(value = "SELECT * FROM categories c WHERE c.id = :id", nativeQuery = true)
    Optional<Category> findById(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM categories c",
            countQuery = "SELECT COUNT(*) FROM categories",
            nativeQuery = true
    )
    Page<Category> findAll(Pageable pageable);
}
