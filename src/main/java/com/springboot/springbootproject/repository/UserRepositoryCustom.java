package com.springboot.springbootproject.repository;

import java.util.List;
import java.util.Optional;

import com.springboot.springbootproject.dto.response.UserResponse;

public interface UserRepositoryCustom {
    boolean existsByUsername(String username);

    Optional<UserResponse> findByUsername(String username);

    Optional<UserResponse> findByIdCustom(String id);

    List<UserResponse> findAllCustom();
}
