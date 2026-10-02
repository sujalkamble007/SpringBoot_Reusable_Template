package com.sujal.springboottemplate.exception;


import com.sujal.springboottemplate.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                exception.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage()
                        )
                );

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Request validation failed",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
