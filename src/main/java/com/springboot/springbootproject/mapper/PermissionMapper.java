package com.springboot.springbootproject.mapper;

import org.mapstruct.Mapper;

import com.springboot.springbootproject.dto.request.PermissionRequest;
import com.springboot.springbootproject.dto.response.PermissionResponse;
import com.springboot.springbootproject.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
