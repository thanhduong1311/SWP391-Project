package com.demo.homemate.services;

import com.demo.homemate.configurations.JwtServiceTest;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.mappings.Implement.AccountMapper;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private  EmployeeRepository employeeRepository;
    @Autowired
    private  AdminRepository adminRepository;

    @Autowired
    private  UserService userService;

    private final JwtServiceTest jwtService;
    private  AuthenticationManager authenticationManager;




//    private final String STUDENT_DOMAIN = "fpt.edu.vn";

    @Override
    /**
     * Login for admin and event managers
     */
    public AuthenticationResponse authentication(AuthenticationRequest request) {

           int check =  userService.checkLogin(request);


           if(check ==1 ) {
               var user = employeeRepository.findByUsername(request.getUsername());
//               var jwtToken = jwtService.generateToken(user);

               var jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJc3N1ZXIiOiJJc3N1ZXIiLCJJc3N1ZWQgQXQiOiIyMDIzLTEwLTA2VDEwOjI5OjM3LjE4M1oiLCJFeHBpcmF0aW9uIjoiMjAyMy0xMC0wNlQxMDoyOTozNy4xODNaIiwiVXNlcm5hbWUiOiJBZG1pbiIsIlJvbGUiOiJBZG1pbiJ9.LB6BdxFnzzYSHzxIEkoWbgvcCe6MZ-06p2RGc8pNAeo";
               return new AuthenticationResponse()
                       .setToken(jwtToken)
                       .setPageReturn("home")
                       .setAccountResponse( new AccountMapper().toEmployeeResponse(user))
                       .setStateCode(1);
           } else if (check == 2){
               var user = customerRepository.findByUsername(request.getUsername());

               var jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJc3N1ZXIiOiJJc3N1ZXIiLCJJc3N1ZWQgQXQiOiIyMDIzLTEwLTA2VDEwOjI5OjM3LjE4M1oiLCJFeHBpcmF0aW9uIjoiMjAyMy0xMC0wNlQxMDoyOTozNy4xODNaIiwiVXNlcm5hbWUiOiJBZG1pbiIsIlJvbGUiOiJBZG1pbiJ9.LB6BdxFnzzYSHzxIEkoWbgvcCe6MZ-06p2RGc8pNAeo";
//               var jwtToken = jwtService.generateToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken)
                       .setPageReturn("home")
                       .setAccountResponse(new AccountMapper().toCustomerResponse(user))
                       .setStateCode(1);
           } else if (check == 3) {
               var user = adminRepository.findByUsername(request.getUsername());
               var jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJc3N1ZXIiOiJJc3N1ZXIiLCJJc3N1ZWQgQXQiOiIyMDIzLTEwLTA2VDEwOjI5OjM3LjE4M1oiLCJFeHBpcmF0aW9uIjoiMjAyMy0xMC0wNlQxMDoyOTozNy4xODNaIiwiVXNlcm5hbWUiOiJBZG1pbiIsIlJvbGUiOiJBZG1pbiJ9.LB6BdxFnzzYSHzxIEkoWbgvcCe6MZ-06p2RGc8pNAeo";
//               var jwtToken = jwtService.generateToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken)
                       .setPageReturn("home")
                       .setAccountResponse(new AccountMapper().toAdminResponse(user))
                       .setStateCode(1);
           } else {
                   return new AuthenticationResponse().setPageReturn("index").setStateCode(0);
           }
    }



    @Override
    public Optional<Employee> getCurrentAuthenticatedAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return Optional.ofNullable(employeeRepository.findByUsername(((UserDetails) principal).getUsername()));
        }
        return Optional.ofNullable(employeeRepository.findByUsername(principal.toString()));
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
