package com.springboot.springbootproject.service.implement;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.springboot.springbootproject.dto.request.PermissionRequest;
import com.springboot.springbootproject.dto.response.PermissionResponse;
import com.springboot.springbootproject.entity.Permission;
import com.springboot.springbootproject.mapper.PermissionMapper;
import com.springboot.springbootproject.repository.PermissionRepository;
import com.springboot.springbootproject.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        Permission saved = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(saved);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAll() {
        log.info("Fetching all permissions using EntityManager query");
        return permissionRepository.findAllCustom();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String permissionId) {
        log.info("Deleting permission with id: {}", permissionId);
        permissionRepository.deleteById(permissionId);
    }
}
