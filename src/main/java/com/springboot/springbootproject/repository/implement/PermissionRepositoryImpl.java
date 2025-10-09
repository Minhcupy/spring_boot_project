package com.springboot.springbootproject.repository.implement;

import com.springboot.springbootproject.dto.response.PermissionResponse;
import com.springboot.springbootproject.entity.Permission;
import com.springboot.springbootproject.repository.PermissionRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class PermissionRepositoryImpl implements PermissionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PermissionResponse> findAllCustom() {
        String sql = """
            SELECT 
                p.name,
                p.description
            FROM permission p
            ORDER BY p.name ASC
        """;

        return entityManager
                .createNativeQuery(sql, "PermissionResponseMapping")
                .getResultList();
    }

    @Override
    public Optional<PermissionResponse> findByIdCustom(String name) {
        String sql = """
            SELECT 
                p.name,
                p.description
            FROM permission p
            WHERE p.name = ?1
        """;

        List<PermissionResponse> result = entityManager
                .createNativeQuery(sql, "PermissionResponseMapping")
                .setParameter(1, name)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        String sql = "DELETE FROM permission WHERE name = ?1";
        entityManager.createNativeQuery(sql)
                .setParameter(1, name)
                .executeUpdate();
    }

    @Override
    public Optional<Permission> findEntityByName(String name) {
        String sql = "SELECT * FROM permission WHERE name = ?1";
        List<Permission> result = entityManager
                .createNativeQuery(sql, Permission.class)
                .setParameter(1, name)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
