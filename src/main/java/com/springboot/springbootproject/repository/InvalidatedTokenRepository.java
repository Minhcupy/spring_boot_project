package com.springboot.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springboot.springbootproject.entity.InvalidatedToken;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

    @Query(value = "SELECT COUNT(*) FROM invalidated_token WHERE id = :id", nativeQuery = true)
    int countByTokenId(@Param("id") String id);

    default boolean existsByTokenId(String id) {
        return countByTokenId(id) > 0;
    }

    @Query(value = "SELECT * FROM invalidated_token WHERE id = :id LIMIT 1", nativeQuery = true)
    Optional<InvalidatedToken> findById(@Param("id") String id);

    @Query(value = "SELECT * FROM invalidated_token", nativeQuery = true)
    List<InvalidatedToken> findAll();
}
