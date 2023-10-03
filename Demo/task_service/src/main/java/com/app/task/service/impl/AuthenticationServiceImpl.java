package com.app.task.service.impl;

import com.app.task.config.JwtService;
import com.app.task.dto.auth.request.AuthenticationRequest;
import com.app.task.dto.auth.request.GoogleLoginRequest;
import com.app.task.dto.auth.response.AuthenticationResponse;
import com.app.task.entity.Account;
import com.app.task.enums.ResponseCode;
import com.app.task.enums.Role;
import com.app.task.exception.ApiException;
import com.app.task.repository.AccountRepository;
import com.app.task.service.AuthenticationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final NetHttpTransport transport;

    private final GsonFactory gsonFactory;

    @Value("${google.client-id}")
    private String cliendId;

    private final String STUDENT_DOMAIN = "fpt.edu.vn";

    @Override
    /**
     * Login for admin and event managers
     */
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));

            var user = accountRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ApiException("Invalid username or password"));

            var jwtToken = jwtService.generateToken(user);
            var jwtRefreshToken = jwtService.generateRefreshToken(user);

            return new AuthenticationResponse()
                    .setToken(jwtToken)
                    .setRefreshToken(jwtRefreshToken);
        } catch (Exception ex) {
            log.error("Authentication error: {}", ex.getMessage());
        }
        throw new ApiException(ResponseCode.AUTH_ERROR_INVALID_USERNAME_OR_PASSWORD);
    }

    @Override
    public AuthenticationResponse refreshToken(String token, HttpServletRequest request) {
        try {
            Long id = jwtService.extractId(token);

            Optional<Account> userDetails = id != null ? accountRepository.findById(id) : Optional.empty();
            if (userDetails.isPresent() && jwtService.isTokenValid(token, userDetails.get())) {
                String accessToken = jwtService.generateToken(userDetails.get());
                String newRefreshToken = jwtService.generateRefreshToken(userDetails.get());
                return new AuthenticationResponse()
                        .setToken(accessToken)
                        .setRefreshToken(newRefreshToken);
            }
        } catch (Exception ex) {
            log.error("Refresh token error: {}", ex.getMessage());
        }

        throw new ApiException("Invalid refresh token");
    }

    @Override
    public Optional<Account> getCurrentAuthenticatedAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return accountRepository.findByUsername(((UserDetails) principal).getUsername());
        }
        return accountRepository.findByUsername(principal.toString());
    }

    @Override
    public Optional<String> getCurrentAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return Optional.of(username);
    }

    @Override
    public List<String> getCurrentAuthenticationRoles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = new ArrayList<>();
        if (principal instanceof UserDetails) {
            roles = ((UserDetails) principal).getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }
        return roles;
    }

    @Override
    public boolean isAdmin() {
        return getCurrentAuthenticationRoles()
                .stream().anyMatch(role -> Objects.equals(role, Role.ADMIN.toString()));
    }

    @Override
    public AuthenticationResponse googleLogin(GoogleLoginRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, gsonFactory)
                    .setAudience(Collections.singletonList(cliendId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken == null || idToken.getPayload() == null || StringUtils.isEmpty(idToken.getPayload().getEmail())) {
                throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Get profile information from payload
            String email = payload.getEmail();
            Optional<Account> accountOpt = accountRepository.findFirstByEmail(email);

            Account account = accountOpt.get();
            String token = jwtService.generateToken(account);
            String refreshToken = jwtService.generateRefreshToken(account);
            return new AuthenticationResponse(token, refreshToken);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        throw new ApiException(ResponseCode.AUTH_ERROR_NOT_PERMITTED);
    }
}
