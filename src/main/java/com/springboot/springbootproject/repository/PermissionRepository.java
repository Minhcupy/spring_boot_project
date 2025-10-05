package com.springboot.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.Permission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    @Query(value = "SELECT * FROM permission", nativeQuery = true)
    List<Permission> findAll();

    @Query(value = "SELECT * FROM permission p WHERE p.id = :id LIMIT 1", nativeQuery = true)
    Optional<Permission> findById(@Param("id") String id);

}
