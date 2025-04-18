package com.springboot.springbootproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.springboot.springbootproject.dto.request.UserCreationRequest;
import com.springboot.springbootproject.dto.request.UserUpdateRequest;
import com.springboot.springbootproject.dto.response.UserResponse;
import com.springboot.springbootproject.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
