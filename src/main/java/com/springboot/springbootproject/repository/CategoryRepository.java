package com.springboot.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {}
