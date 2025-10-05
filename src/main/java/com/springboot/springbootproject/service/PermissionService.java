package com.springboot.springbootproject.service;

import java.util.List;

import com.springboot.springbootproject.dto.request.PermissionRequest;
import com.springboot.springbootproject.dto.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
