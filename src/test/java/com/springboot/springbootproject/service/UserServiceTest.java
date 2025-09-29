package com.springboot.springbootproject.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import com.springboot.springbootproject.service.implement.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.springboot.springbootproject.dto.request.UserCreationRequest;
import com.springboot.springbootproject.dto.response.UserResponse;
import com.springboot.springbootproject.entity.User;
import com.springboot.springbootproject.exception.AppException;
import com.springboot.springbootproject.repository.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2004, 7, 24);

        request = UserCreationRequest.builder()
                .username("Minh")
                .firstName("Trinh")
                .lastName("Minh")
                .password("12345678")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("a711c729eea3")
                .username("Minh")
                .firstName("Trinh")
                .lastName("Minh")
                .dob(dob)
                .build();

        user = User.builder()
                .id("a711c729eea3")
                .username("Minh")
                .firstName("Trinh")
                .lastName("Minh")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);
        // THEN

        Assertions.assertThat(response.getId()).isEqualTo("a711c729eea3");
        Assertions.assertThat(response.getUsername()).isEqualTo("Minh");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> {
            userService.createUser(request);
        });

        assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "Minh")
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("Minh");
        Assertions.assertThat(response.getId()).isEqualTo("a711c729eea3");
    }

    @Test
    @WithMockUser(username = "Minh")
    void getMyInfo_userNotFound_fail() {
        // GIVEN
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        // WHEN
        var exception = assertThrows(AppException.class, () -> {
            userService.getMyInfo();
        });

        assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}

// mvn clean test jacoco:report
// ./gradlew clean test jacocoTestReport
// mvn spotless:apply
