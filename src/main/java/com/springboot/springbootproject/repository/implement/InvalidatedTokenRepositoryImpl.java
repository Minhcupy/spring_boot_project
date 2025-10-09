package com.springboot.springbootproject.repository.implement;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.springbootproject.entity.InvalidatedToken;
import com.springboot.springbootproject.repository.InvalidatedTokenRepositoryCustom;

@Repository
@Transactional(readOnly = true)
public class InvalidatedTokenRepositoryImpl implements InvalidatedTokenRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByTokenId(String id) {
        String sql = "SELECT COUNT(*) FROM invalidated_token WHERE id = ?1";

        Long count = ((Number)
                        entityManager.createNativeQuery(sql).setParameter(1, id).getSingleResult())
                .longValue();

        return count > 0;
    }

    @Override
    public Optional<InvalidatedToken> findByIdCustom(String id) {
        String sql = "SELECT * FROM invalidated_token WHERE id = ?1";

        List<InvalidatedToken> results = entityManager
                .createNativeQuery(sql, InvalidatedToken.class)
                .setParameter(1, id)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<InvalidatedToken> findAllCustom() {
        String sql = """
			SELECT *
			FROM invalidated_token
			ORDER BY expiry_time DESC
		""";

        return entityManager.createNativeQuery(sql, InvalidatedToken.class).getResultList();
    }
}
