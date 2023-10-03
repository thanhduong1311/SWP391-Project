package com.app.task.exception;

import com.app.task.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiException extends RuntimeException {
    private String message;
    private int errorCode;

    public ApiException(String message) {
        super(message);
        this.message = message;
        this.errorCode = HttpStatus.BAD_REQUEST.value();
    }

    public ApiException(ResponseCode code) {
        super(code.getMessage());
        this.errorCode = code.getCode();
        this.message = code.getMessage();
    }

    public ApiException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
