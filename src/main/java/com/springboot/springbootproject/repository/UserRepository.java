package com.springboot.springbootproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(
            value = "SELECT COUNT(*) FROM user u WHERE u.username = :username",
            nativeQuery = true
    )
    int existsByUsernameRaw(@Param("username") String username);

    default boolean existsByUsername(String username) {
        return existsByUsernameRaw(username) > 0;
    }

    @Query(
            value = "SELECT * FROM user u WHERE u.username = :username LIMIT 1",
            nativeQuery = true
    )
    Optional<User> findByUsername(@Param("username") String username);

    @Query(
            value = "SELECT * FROM user u WHERE u.id = :id LIMIT 1",
            nativeQuery = true
    )
    Optional<User> findById(@Param("id") String id);

    @Query(
            value = "SELECT * FROM user",
            nativeQuery = true
    )
    List<User> findAll();
}
