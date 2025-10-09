package com.springboot.springbootproject.repository;

import com.springboot.springbootproject.dto.response.PermissionResponse;
import com.springboot.springbootproject.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepositoryCustom {

    List<PermissionResponse> findAllCustom();

    Optional<PermissionResponse> findByIdCustom(String id);

    void deleteByName(String name);

    Optional<Permission> findEntityByName(String name);
}
