package com.example.brandservice.exception;

import com.example.brandservice.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        /*Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );*/

        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .reduce("", (acc, err) -> acc + err + "; ");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.builder()
                        .code(ErrorCode.BAD_REQUEST.getCode())
                        .message(errors)
                        .build()
        );
    }





    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAccountAlreadyExistsException(AccountAlreadyExistsException ex) {
        log.error("Account already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.builder()
                        .code(ErrorCode.USER_EXISTED.getCode())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(TokenVerificationException.class)
    public ResponseEntity<ApiResponse> handleTokenVerificationException(TokenVerificationException ex) {
        log.error("Authentication failure: Token missing, invalid or expired: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponse.builder()
                        .code(ErrorCode.UNAUTHORIZED.getCode())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericExceptions(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.builder()
                        .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                        .message(ex.getMessage())
                        .build()
                );
    }
}