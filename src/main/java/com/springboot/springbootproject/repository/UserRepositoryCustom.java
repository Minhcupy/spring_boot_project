package com.springboot.springbootproject.repository;

import com.springboot.springbootproject.dto.response.UserResponse;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    boolean existsByUsername(String username);
    Optional<UserResponse> findByUsername(String username);
    Optional<UserResponse> findByIdCustom(String id);
    List<UserResponse> findAllCustom();
}
