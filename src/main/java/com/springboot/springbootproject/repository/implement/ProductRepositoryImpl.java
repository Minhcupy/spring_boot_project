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

import com.springboot.springbootproject.dto.response.ProductResponse;
import com.springboot.springbootproject.repository.ProductRepositoryCustom;

@Repository
@Transactional(readOnly = true)
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ProductResponse> findByIdCustom(Long id) {
        String sql =
                """
			SELECT
				p.id,
				p.name,
				c.id AS category_id,
				c.name AS category_name,
				p.quantity,
				p.price,
				p.description,
				p.image_url
			FROM product p
			JOIN categories c ON p.category_id = c.id
			WHERE p.id = ?1
		""";

        List<ProductResponse> result = entityManager
                .createNativeQuery(sql, "ProductResponseMapping")
                .setParameter(1, id)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Page<ProductResponse> findAllCustom(Pageable pageable) {
        String sql =
                """
			SELECT
				p.id,
				p.name,
				c.id AS category_id,
				c.name AS category_name,
				p.quantity,
				p.price,
				p.description,
				p.image_url
			FROM product p
			JOIN categories c ON p.category_id = c.id
			ORDER BY p.id ASC
			LIMIT ?1 OFFSET ?2
		""";

        List<ProductResponse> products = entityManager
                .createNativeQuery(sql, "ProductResponseMapping")
                .setParameter(1, pageable.getPageSize())
                .setParameter(2, pageable.getOffset())
                .getResultList();

        Long total = ((Number) entityManager
                        .createNativeQuery("SELECT COUNT(*) FROM product")
                        .getSingleResult())
                .longValue();

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public Page<ProductResponse> findByCategoryId(Long categoryId, Pageable pageable) {
        String sql =
                """
			SELECT
				p.id,
				p.name,
				c.id AS category_id,
				c.name AS category_name,
				p.quantity,
				p.price,
				p.description,
				p.image_url
			FROM product p
			JOIN categories c ON p.category_id = c.id
			WHERE c.id = ?1
			ORDER BY p.id ASC
			LIMIT ?2 OFFSET ?3
		""";

        List<ProductResponse> products = entityManager
                .createNativeQuery(sql, "ProductResponseMapping")
                .setParameter(1, categoryId)
                .setParameter(2, pageable.getPageSize())
                .setParameter(3, pageable.getOffset())
                .getResultList();

        Long total = ((Number) entityManager
                        .createNativeQuery("SELECT COUNT(*) FROM product WHERE category_id = ?1")
                        .setParameter(1, categoryId)
                        .getSingleResult())
                .longValue();

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public List<ProductResponse> findByNameContainingIgnoreCase(String keyword) {
        String sql =
                """
			SELECT
				p.id,
				p.name,
				c.id AS category_id,
				c.name AS category_name,
				p.quantity,
				p.price,
				p.description,
				p.image_url
			FROM product p
			JOIN categories c ON p.category_id = c.id
			WHERE LOWER(p.name) LIKE ?1
			ORDER BY p.name ASC
		""";

        return entityManager
                .createNativeQuery(sql, "ProductResponseMapping")
                .setParameter(1, "%" + keyword.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public boolean existsByCategoryIdCustom(Long categoryId) {
        String sql = "SELECT COUNT(*) FROM product WHERE category_id = ?1";

        Long count = ((Number) entityManager
                        .createNativeQuery(sql)
                        .setParameter(1, categoryId)
                        .getSingleResult())
                .longValue();

        return count > 0;
    }
}
