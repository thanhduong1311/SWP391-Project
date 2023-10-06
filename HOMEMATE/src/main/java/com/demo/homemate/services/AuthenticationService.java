package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface AuthenticationService {

    /**
     * xác thực người dùng và trả về các thông tin của AuthenticationResponse
     * @param request
     * @return
     */
    AuthenticationResponse authentication(AuthenticationRequest request);

//    AuthenticationResponse refreshToken(String token, HttpServletRequest request);

    Optional<Employee> getCurrentAuthenticatedAccount();
//    Optional<Customer> getCurrentAuthenticatedAccount();
//    Optional<Admin> getCurrentAuthenticatedAccount();

    Optional<String> getCurrentAuthentication();

    List<String> getCurrentAuthenticationRoles();

    boolean isAdmin();

}
