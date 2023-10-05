package com.demo.student.services;

import com.demo.student.configurations.JwtService;
import com.demo.student.dtos.auth.request.AuthenticationRequest;
import com.demo.student.dtos.auth.response.AuthenticationResponse;
import com.demo.student.entities.BaseAccount;
import com.demo.student.entities.Employee;
import com.demo.student.repositories.AdminRepository;
import com.demo.student.repositories.CustomerRepository;
import com.demo.student.repositories.EmployeeRepository;
import enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final AdminRepository adminRepository;

    private  UserService userService;

    private final JwtService jwtService;

    private  AuthenticationManager authenticationManager;


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


           int check =  userService.checkLogin(request);


           if(check ==1 ) {
               var user = employeeRepository.findByUsername(request.getUsername())
                       .orElseThrow(() -> new Exception("Invalid username or password"));
               var jwtToken = jwtService.generateToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken);
           } else if (check == 2){
               var user = customerRepository.findByUsername(request.getUsername())
                       .orElseThrow(() -> new Exception("Invalid username or password"));
               var jwtToken = jwtService.generateToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken);
           } else if (check == 3) {
               var user = adminRepository.findByUsername(request.getUsername())
                       .orElseThrow(() -> new Exception("Invalid username or password"));
               var jwtToken = jwtService.generateToken(user);
               return new AuthenticationResponse()
                       .setToken(jwtToken);
           } else {
               throw new Exception("Invalid username or password");
           }


        } catch (Exception ex) {
            log.error("Authentication error: {}", ex.getMessage());
        }
        String mess = "Invalid username or password";
        try {
            throw new Exception(mess);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Optional<Employee> getCurrentAuthenticatedAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return employeeRepository.findByUsername(((UserDetails) principal).getUsername());
        }
        return employeeRepository.findByUsername(principal.toString());
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
