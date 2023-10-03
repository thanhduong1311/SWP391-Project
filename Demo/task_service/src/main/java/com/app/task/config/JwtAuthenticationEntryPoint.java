package com.app.task.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        response.setStatus(apiError.getErrorCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ServletOutputStream out = response.getOutputStream();
        new ObjectMapper().writeValue(out, apiError);

        out.flush();
    }
}
