package com.springboot.springbootproject.service;

import com.springboot.springbootproject.dto.request.PermissionRequest;
import com.springboot.springbootproject.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
