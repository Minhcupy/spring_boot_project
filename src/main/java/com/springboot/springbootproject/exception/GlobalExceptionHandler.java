package com.springboot.springbootproject.exception;

import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.springbootproject.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

//    private static final String MIN_ATTRIBUTE = "min";
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<String>> runtimeExceptionHandler(RuntimeException e) {
        log.error("[RuntimeException] {}", e.getMessage(), e);

        var apiResponse = ApiResponse.<String>builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(e.getMessage() != null ? e.getMessage() : ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<String>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("[AppException] {} - {}", errorCode.getCode(), errorCode.getMessage());

        var apiResponse = ApiResponse.<String>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse<String>> handleAccessDenied(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        log.warn("[AccessDenied] {}", e.getMessage());

        var apiResponse = ApiResponse.<String>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException e) {
        // Lấy toàn bộ lỗi field và gộp thành chuỗi dễ đọc
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> formatValidationError(fieldError))
                .collect(Collectors.joining("; "));

        log.warn("[ValidationError] {}", message);

        var apiResponse = ApiResponse.<String>builder()
                .code(ErrorCode.INVALID_KEY.getCode())
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiResponse<String>> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        log.warn("[ConstraintViolation] {}", message);

        var apiResponse = ApiResponse.<String>builder()
                .code(ErrorCode.INVALID_KEY.getCode())
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String formatValidationError(FieldError fieldError) {
        return String.format(
                "Field '%s' %s ",
                fieldError.getField(),
                fieldError.getDefaultMessage()
//                Objects.toString(fieldError.getRejectedValue(), "null")
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ApiResponse<String>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = e.getMessage();

        // Kiểm tra lỗi duplicate product name + category
        if (message != null && message.contains("unique_name_category")) {
            message = "Sản phẩm đã tồn tại trong danh mục này!";
        } else {
            message = "Dữ liệu không hợp lệ hoặc trùng lặp!";
        }

        log.warn("[DataIntegrityViolation] {}", message);

        var apiResponse = ApiResponse.<String>builder()
                .code(ErrorCode.INVALID_KEY.getCode())
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
