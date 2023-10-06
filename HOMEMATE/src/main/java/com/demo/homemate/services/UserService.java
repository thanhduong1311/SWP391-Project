package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private  EmployeeRepository employeeRepository;
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private  AdminRepository adminRepository;


    @Override
    public int checkUsername(String username) {
        Employee e = employeeRepository.findByUsername(username);
        Customer c = customerRepository.findByUsername(username);
        Admin a = adminRepository.findByUsername(username);

        if (e == null && a == null && c == null) {
            return 0;
        } else {
            if (c == null && a == null) {
                return 1;
            }
            if (e == null && a == null) {
                return 2;
            }
            if (e == null && c == null) {
                return 3;
            }
        }
        return 0;
    }

    @Override
    public int checkEmail(String email) {
        Employee e = employeeRepository.findByEmail(email);
        Customer c = customerRepository.findByEmail(email);

        if (e == null && c == null) {
            return 0;
        } else {
            if (e == null) {
                return 1;
            }
            if (c == null) {
                return 2;
            }
        }
        return 0;
    }

    @Override
    public int checkLogin(AuthenticationRequest request) {
        int var = checkUsername(request.getUsername());

        if (var == 0) {
            return 0;
        } else if (var == 1) {
            Employee e = employeeRepository.findByUsername(request.getUsername());
            if (e.getUsername().equals(request.getUsername()) &&
                    e.getPassword().equals(request.getPassword())
            ) {
                return 1;
            } else return 0;

        } else if (var == 2) {
            Customer c = customerRepository.findByUsername(request.getUsername());
            if (c.getUsername().equals(request.getUsername()) &&
                    c.getPassword().equals(request.getPassword())
            ) {
                return 2;
            } else return 0;
        } else {
            Admin a = adminRepository.findByUsername(request.getUsername());
            if (a.getUsername().equals(request.getUsername()) &&
                    a.getPassword().equals(request.getPassword())
            ) {
                return 3;
            } else return 0;
        }
    }

    @Override
    public int getRole(Customer customer) {
        return customer.getRole().ordinal();
    }

    @Override
    public int getRole(Employee employee) {
        return employee.getRole().ordinal();
    }

    @Override
    public int getRole(Admin admin) {
        return admin.getRole().ordinal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin user = adminRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
