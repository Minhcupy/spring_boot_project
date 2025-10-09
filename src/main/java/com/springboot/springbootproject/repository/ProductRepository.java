package com.springboot.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {}
