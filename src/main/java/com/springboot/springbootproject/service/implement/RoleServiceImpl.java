package com.springboot.springbootproject.service.implement;

import java.util.HashSet;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.springboot.springbootproject.dto.request.RoleRequest;
import com.springboot.springbootproject.dto.response.RoleResponse;
import com.springboot.springbootproject.entity.Role;
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
        if (roleRepository.existsByName(request.getName())) {
            throw new RuntimeException("Role already exists");
        }

        Role role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        Role saved = roleRepository.save(role);
        return roleMapper.toRoleResponse(saved);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    //    public RoleResponse updateRolePermissions(String roleName, Set<String> permissionIds) {
    //        var role = roleRepository.findByName(roleName)
    //                .orElseThrow(() -> new RuntimeException("Role not found"));
    //
    //        // Lấy entity role gốc để cập nhật permissions
    //        Role existingRole = new Role();
    //        existingRole.setName(role.getName());
    //        existingRole.setDescription(role.getDescription());
    //
    //        Set<Permission> newPermissions = new HashSet<>(permissionRepository.findAllById(permissionIds));
    //        existingRole.setPermissions(newPermissions);
    //
    //        existingRole = roleRepository.save(existingRole);
    //        return roleMapper.toRoleResponse(existingRole);
    //    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAll() {
        log.info("Fetching all roles using EntityManager query");
        return roleRepository.findAllCustom();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String roleId) {
        log.info("Deleting role with id: {}", roleId);
        roleRepository.deleteByIdCustom(roleId);
    }
}
