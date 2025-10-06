package com.springboot.springbootproject.service.implement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.springboot.springbootproject.entity.Permission;
import jakarta.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.springboot.springbootproject.dto.request.RoleRequest;
import com.springboot.springbootproject.dto.response.RoleResponse;
import com.springboot.springbootproject.mapper.RoleMapper;
import com.springboot.springbootproject.repository.PermissionRepository;
import com.springboot.springbootproject.repository.RoleRepository;
import com.springboot.springbootproject.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsById(request.getName())) {
            throw new RuntimeException("Role already exists");
        }
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

//    @Override
//    @PreAuthorize("hasRole('ADMIN')")
//      public RoleResponse updateRolePermissions(String roleName, Set<String> permissions) {
//            var role = roleRepository.findByName(roleName)
//                    .orElseThrow(() -> new RuntimeException("Role not found"));
//
//          Set<Permission> newPermissions = new HashSet<>(permissionRepository.findAllById(permissions));
//          role.setPermissions(newPermissions);
//
//            role = roleRepository.save(role);
//            return roleMapper.toRoleResponse(role);
//        }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
