package com.springboot.springbootproject.controller;

import java.util.List;
import java.util.Set;

import com.springboot.springbootproject.dto.request.RoleRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.springboot.springbootproject.dto.request.UserCreationRequest;
import com.springboot.springbootproject.dto.request.UserUpdateRequest;
import com.springboot.springbootproject.dto.response.ApiResponse;
import com.springboot.springbootproject.dto.response.UserResponse;
import com.springboot.springbootproject.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}/roles")
    public ApiResponse<UserResponse> updateUserRole(
            @PathVariable String userId,
            @RequestBody RoleRequest roleRequest
    ) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUserRole(userId, roleRequest))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }
}
