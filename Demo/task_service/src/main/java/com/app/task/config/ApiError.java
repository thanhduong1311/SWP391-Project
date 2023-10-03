package com.app.task.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@ToString
public class ApiError {
    private HttpStatus status;
    private int errorCode;
    private String message;
    private List<String> errors;

    public ApiError() {

    }

    public ApiError(HttpStatus status) {
        this.status = status;
        this.errorCode = status.value();
        this.message = status.getReasonPhrase();
    }

    public ApiError(HttpStatus status, int errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiError(HttpStatus status, int errorCode, String message, List<String> errors) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

}
