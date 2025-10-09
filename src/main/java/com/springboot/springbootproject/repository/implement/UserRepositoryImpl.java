package com.springboot.springbootproject.repository.implement;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.springbootproject.dto.response.UserResponse;
import com.springboot.springbootproject.repository.UserRepositoryCustom;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByUsername(String username) {
        String sql = """
			SELECT COUNT(*)
			FROM `user`
			WHERE LOWER(username) = LOWER(?1)
		""";

        Long count = ((Number) entityManager
                        .createNativeQuery(sql)
                        .setParameter(1, username)
                        .getSingleResult())
                .longValue();

        return count > 0;
    }

    @Override
    public Optional<UserResponse> findByUsername(String username) {
        String sql =
                """
			SELECT
				u.id,
				u.username,
				u.first_name,
				u.last_name,
				u.dob,
				r.name AS role_name
			FROM `user` u
			LEFT JOIN user_roles ur ON u.id = ur.user_id
			LEFT JOIN role r ON ur.roles_name = r.name
			WHERE u.username = ?1
		""";

        List<UserResponse> result = entityManager
                .createNativeQuery(sql, "UserResponseMapping")
                .setParameter(1, username)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<UserResponse> findByIdCustom(String id) {
        String sql =
                """
			SELECT
				u.id,
				u.username,
				u.first_name,
				u.last_name,
				u.dob,
				r.name AS role_name
			FROM `user` u
			LEFT JOIN user_roles ur ON u.id = ur.user_id
			LEFT JOIN role r ON ur.roles_name = r.name
			WHERE u.id = ?1
		""";

        List<UserResponse> result = entityManager
                .createNativeQuery(sql, "UserResponseMapping")
                .setParameter(1, id)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<UserResponse> findAllCustom() {
        String sql =
                """
			SELECT
				u.id,
				u.username,
				u.first_name,
				u.last_name,
				u.dob,
				r.name AS role_name
			FROM `user` u
			LEFT JOIN user_roles ur ON u.id = ur.user_id
			LEFT JOIN role r ON ur.roles_name = r.name
			ORDER BY u.username ASC
		""";

        return entityManager.createNativeQuery(sql, "UserResponseMapping").getResultList();
    }
}
