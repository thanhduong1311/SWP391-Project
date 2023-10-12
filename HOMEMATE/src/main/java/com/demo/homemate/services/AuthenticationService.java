package com.demo.homemate.services;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.enums.Role;
import com.demo.homemate.services.interfaces.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {


    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final AdminRepository adminRepository;

    private final UserService userService;

    private AuthenticationManager authenticationManager;

//    private final String STUDENT_DOMAIN = "fpt.edu.vn";

    @Override
    /**
     * Login for admin and event managers
     */
    public AuthenticationResponse authentication(AuthenticationRequest request) {


           int check =  userService.checkLogin(request);


           if(check ==1 ) {
               var user = employeeRepository.findByUsername(request.getUsername());
               String jwtToken = JWTService.generateJwtEmployeeToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken)
                       .setAccountResponse( new AccountMapper().toEmployeeResponse(user))
                       .setStateCode(1);
           } else if (check == 2){
               var user = customerRepository.findByUsername(request.getUsername());
               String jwtToken = JWTService.generateJwtCustomerToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken)
                       .setAccountResponse(new AccountMapper().toCustomerResponse(user))
                       .setStateCode(1);
           } else if (check == 3) {
               var user = adminRepository.findByUsername(request.getUsername());
               String jwtToken = JWTService.generateJwtAdminToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken)
                       .setAccountResponse(new AccountMapper().toAdminResponse(user))
                       .setStateCode(1);
           } else {
                   return new AuthenticationResponse().setStateCode(0);
           }
    }



    @Override
    public Optional<Admin> getCurrentAuthenticatedAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return Optional.ofNullable(adminRepository.findByUsername(((UserDetails) principal).getUsername()));
        }
        return Optional.ofNullable(adminRepository.findByUsername(principal.toString()));
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

}
