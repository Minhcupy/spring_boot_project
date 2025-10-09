package com.springboot.springbootproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
    @Query(
            "SELECT u FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.permissions p WHERE u.username = :username")
    Optional<User> findEntityByUsername(@Param("username") String username);
}
