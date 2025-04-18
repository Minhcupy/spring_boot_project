package com.springboot.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
