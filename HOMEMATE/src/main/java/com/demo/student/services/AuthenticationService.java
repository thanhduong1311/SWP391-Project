package com.demo.student.services;

import com.demo.student.dtos.auth.request.AuthenticationRequest;
import com.demo.student.dtos.auth.response.AuthenticationResponse;
import com.demo.student.entities.Admin;
import com.demo.student.entities.Customer;
import com.demo.student.entities.Employee;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface AuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest request);

//    AuthenticationResponse refreshToken(String token, HttpServletRequest request);

    Optional<Employee> getCurrentAuthenticatedAccount();
//    Optional<Customer> getCurrentAuthenticatedAccount();
//    Optional<Admin> getCurrentAuthenticatedAccount();

    Optional<String> getCurrentAuthentication();

    List<String> getCurrentAuthenticationRoles();

    boolean isAdmin();

}
