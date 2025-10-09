package com.springboot.springbootproject.service;

import java.util.List;

import com.springboot.springbootproject.dto.request.RoleRequest;
import com.springboot.springbootproject.dto.response.RoleResponse;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    //    RoleResponse updateRolePermissions(String roleName, Set<String> permissions);

    void delete(String role);
}
