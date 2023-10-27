package com.demo.homemate.controllers.common;

import com.demo.homemate.dtos.error.MyErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalHandleExceptionController {
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(Exception e) {
        MyErrorResponse errorResponse = new MyErrorResponse(404, "Không tìm thấy");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
