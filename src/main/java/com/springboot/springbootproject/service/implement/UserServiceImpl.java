package com.springboot.springbootproject.service.implement;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.springbootproject.dto.request.RoleRequest;
import jakarta.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.springbootproject.constant.PredefinedRole;
import com.springboot.springbootproject.dto.request.UserCreationRequest;
import com.springboot.springbootproject.dto.request.UserUpdateRequest;
import com.springboot.springbootproject.dto.response.UserResponse;
import com.springboot.springbootproject.entity.Role;
import com.springboot.springbootproject.entity.User;
import com.springboot.springbootproject.exception.AppException;
import com.springboot.springbootproject.exception.ErrorCode;
import com.springboot.springbootproject.mapper.UserMapper;
import com.springboot.springbootproject.repository.RoleRepository;
import com.springboot.springbootproject.repository.UserRepository;
import com.springboot.springbootproject.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        // ✅ 1. Kiểm tra username đã tồn tại
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // ✅ 2. Chuyển DTO -> Entity và mã hoá mật khẩu
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ 3. Lấy role mặc định (USER)
        Role defaultRole = roleRepository.findEntityByName(PredefinedRole.USER_ROLE)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        user.setRoles(new HashSet<>(List.of(defaultRole)));

        // ✅ 4. Lưu user mới và map lại sang DTO
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }


    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUserRole(String userId, RoleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // ⬇️ Dùng hàm trả về Role (entity), KHÔNG dùng findByName() trả về RoleResponse
        Role role = roleRepository.findEntityByName(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        user.setRoles(new HashSet<>(java.util.Collections.singleton(role)));
        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        log.info("Fetching all users via EntityManager");
        return userRepository.findAllCustom();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        log.info("Fetching user by ID via EntityManager");
        return userRepository.findByIdCustom(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
