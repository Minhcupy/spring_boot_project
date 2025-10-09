package com.springboot.springbootproject.repository;

import java.util.List;
import java.util.Optional;

import com.springboot.springbootproject.dto.response.PermissionResponse;
import com.springboot.springbootproject.entity.Permission;

public interface PermissionRepositoryCustom {

    List<PermissionResponse> findAllCustom();

    Optional<PermissionResponse> findByIdCustom(String id);

    void deleteByName(String name);

    Optional<Permission> findEntityByName(String name);
}
