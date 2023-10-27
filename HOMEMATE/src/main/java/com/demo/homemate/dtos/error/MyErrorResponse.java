package com.demo.homemate.dtos.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class MyErrorResponse implements ErrorResponse {

    public MyErrorResponse(int statusCode, String message) {

    }

    @Override
    public HttpStatusCode getStatusCode() {
        return null;
    }

    @Override
    public ProblemDetail getBody() {
        return null;
    }
}
