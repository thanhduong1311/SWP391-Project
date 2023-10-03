package com.app.task.controller;

import com.app.task.config.OpenApiConfig;
import com.app.task.dto.account.response.AccountDetailResponse;
import com.app.task.dto.auth.request.AuthenticationRequest;
import com.app.task.dto.auth.request.GoogleLoginRequest;
import com.app.task.dto.auth.response.AuthenticationResponse;
import com.app.task.entity.Account;
import com.app.task.enums.ResponseCode;
import com.app.task.exception.ApiException;
import com.app.task.mappings.AccountMapper;
import com.app.task.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AccountMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        var response = authenticationService.authentication(request);
        System.out.printf("Login");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/google")
    public ResponseEntity<AuthenticationResponse> loginGoogle(@Valid @RequestBody GoogleLoginRequest request) {
        var response = authenticationService.googleLogin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-token")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        final String BEARER = "Bearer ";
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            throw new ApiException("Invalid refresh token");
        }

        final String jwt = authHeader.substring(BEARER.length());
        var response = authenticationService.refreshToken(jwt, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Transactional
    public ResponseEntity<AccountDetailResponse> getProfile() {
        Account acc = authenticationService.getCurrentAuthenticatedAccount()
                .orElseThrow(() -> new ApiException(ResponseCode.UNAUTHORIZED));

        AccountDetailResponse accResponse = mapper.toDetailResponse(acc);

        return ResponseEntity.ok(accResponse);
    }
}
