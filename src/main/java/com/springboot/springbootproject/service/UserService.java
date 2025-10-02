package com.springboot.springbootproject.service;

import java.util.List;

import com.springboot.springbootproject.dto.request.UserCreationRequest;
import com.springboot.springbootproject.dto.request.UserUpdateRequest;
import com.springboot.springbootproject.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserCreationRequest request);

    UserResponse getMyInfo();

    UserResponse updateUser(String userId, UserUpdateRequest request);

    void deleteUser(String userId);

    List<UserResponse> getAllUsers();

    UserResponse getUser(String id);
}
