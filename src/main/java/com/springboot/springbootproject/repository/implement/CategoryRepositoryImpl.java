package com.springboot.springbootproject.repository.implement;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.springbootproject.dto.response.CategoryResponse;
import com.springboot.springbootproject.repository.CategoryRepositoryCustom;

@Repository
@Transactional(readOnly = true)
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long existsByName(String name) {
        String sql = """
			SELECT COUNT(*)
			FROM categories
			WHERE LOWER(name) = LOWER(?1)
		""";

        return ((Number) entityManager
                        .createNativeQuery(sql)
                        .setParameter(1, name)
                        .getSingleResult())
                .longValue();
    }

    @Override
    public Optional<CategoryResponse> findByIdCustom(Long id) {
        String sql =
                """
			SELECT
				c.id,
				c.name,
				c.description,
				COUNT(p.id) AS product_count
			FROM categories c
			LEFT JOIN product p ON p.category_id = c.id
			WHERE c.id = ?1
			GROUP BY c.id, c.name, c.description
		""";

        List<CategoryResponse> result = entityManager
                .createNativeQuery(sql, "CategoryResponseMapping")
                .setParameter(1, id)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Page<CategoryResponse> findAllCustom(Pageable pageable) {
        String sql =
                """
			SELECT
				c.id,
				c.name,
				c.description,
				COUNT(p.id) AS product_count
			FROM categories c
			LEFT JOIN product p ON p.category_id = c.id
			GROUP BY c.id, c.name, c.description
			ORDER BY c.id ASC
			LIMIT ?1 OFFSET ?2
		""";

        List<CategoryResponse> categories = entityManager
                .createNativeQuery(sql, "CategoryResponseMapping")
                .setParameter(1, pageable.getPageSize())
                .setParameter(2, pageable.getOffset())
                .getResultList();

        String countSql = "SELECT COUNT(*) FROM categories";
        Long total = ((Number) entityManager.createNativeQuery(countSql).getSingleResult()).longValue();

        return new PageImpl<>(categories, pageable, total);
    }
}
