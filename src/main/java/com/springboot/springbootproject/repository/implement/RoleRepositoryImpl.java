package com.springboot.springbootproject.repository.implement;

import com.springboot.springbootproject.dto.response.PermissionResponse;
import com.springboot.springbootproject.dto.response.RoleResponse;
import com.springboot.springbootproject.entity.Permission;
import com.springboot.springbootproject.entity.Role;
import com.springboot.springbootproject.repository.RoleRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoleResponse> findAllCustom() {
        String sql = """
            SELECT 
                r.name,
                r.description
            FROM role r
            ORDER BY r.name ASC
        """;

        return entityManager
                .createNativeQuery(sql, "RoleResponseMapping")
                .getResultList();
    }

    @Override
    public boolean existsByName(String name) {
        String sql = """
            SELECT COUNT(*) 
            FROM role
            WHERE LOWER(name) = LOWER(?1)
        """;

        Long count = ((Number) entityManager
                .createNativeQuery(sql)
                .setParameter(1, name)
                .getSingleResult()).longValue();

        return count > 0;
    }

    @Override
    public Optional<RoleResponse> findByName(String name) {
        String sql = """
            SELECT 
                r.name,
                r.description
            FROM role r
            WHERE LOWER(r.name) = LOWER(?1)
        """;

        List<RoleResponse> result = entityManager
                .createNativeQuery(sql, "RoleResponseMapping")
                .setParameter(1, name)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    @Transactional
    public void deleteByIdCustom(String name) {
        String sql = "DELETE FROM role WHERE name = ?1";
        entityManager.createNativeQuery(sql)
                .setParameter(1, name)
                .executeUpdate();
    }

    @Override
    public Optional<Role> findEntityByName(String name) {
        String sql = """
            SELECT * FROM role WHERE name = ?1
        """;

        List<Role> result = entityManager
                .createNativeQuery(sql, Role.class)
                .setParameter(1, name)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Role> findEntitiesByIds(List<String> names) {
        String sql = """
            SELECT * FROM role WHERE name IN (:names)
        """;

        return entityManager
                .createNativeQuery(sql, Role.class)
                .setParameter("names", names)
                .getResultList();
    }

    private RoleResponse mapToRoleResponse(Role role) {
        if (role == null) return null;

        Set<PermissionResponse> permissionResponses = null;
        if (role.getPermissions() != null) {
            permissionResponses = role.getPermissions().stream()
                    .map(this::mapToPermissionResponse)
                    .collect(Collectors.toSet());
        }

        return RoleResponse.builder()
                .name(role.getName())
                .description(role.getDescription())
                .permissions(permissionResponses)
                .build();
    }

    private PermissionResponse mapToPermissionResponse(Permission permission) {
        if (permission == null) return null;
        return PermissionResponse.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
