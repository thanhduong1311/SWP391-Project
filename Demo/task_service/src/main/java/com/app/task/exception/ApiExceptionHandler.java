package com.app.task.exception;

import com.app.task.config.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMet(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(status, status.value(), "Invalid argument(s)", errors);
        log.error("API error: {}", apiError);
        return ResponseEntity.status(status).body(apiError);
    }


    @ExceptionHandler({ApiException.class})
    protected ResponseEntity<Object> handleApiException(ApiException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(status, ex.getErrorCode(), ex.getMessage());
        log.error("API error: {}", apiError);
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleInternalException(AccessDeniedException ex) {
        log.error("Access denied: {}", ex.getMessage());
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError apiError = new ApiError(status, status.value(), status.getReasonPhrase(), Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleInternalException(Exception ex) {
        ex.printStackTrace();
        log.error("Internal error: {}", ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError apiError = new ApiError(status, status.value(), status.getReasonPhrase(), Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(status).body(apiError);
    }
}
