package com.demo.student.services;

import com.demo.student.dtos.auth.request.AuthenticationRequest;
import com.demo.student.entities.Admin;
import com.demo.student.entities.Customer;
import com.demo.student.entities.Employee;
import com.demo.student.repositories.AdminRepository;
import com.demo.student.repositories.CustomerRepository;
import com.demo.student.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService implements IUserService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;


    @Override
    public int checkUsername(String username) {
        Optional<Employee> e = employeeRepository.findByUsername(username);
        Optional<Customer> c = customerRepository.findByUsername(username);
        Optional<Admin> a = adminRepository.findByUsername(username);

        if(e== null && a==null && c==null) {
            return 0;
        } else {
            if(c == null && a== null) {
                return 1;
            }
            if (e==null && a == null) {
                return 2;
            }
            if (e==null && c==null) {
                return 3;
            }
        }
        return 0;
    }

    @Override
    public int checkEmail(String email) {
        Optional<Employee> e = employeeRepository.findByUsername(email);
        Optional<Customer> c = customerRepository.findByUsername(email);
        Optional<Admin> a = adminRepository.findByUsername(email);

        if(e== null && a==null && c==null) {
            return 0;
        } else {
            if(c == null && a== null) {
                return 1;
            }
            if (e==null && a == null) {
                return 2;
            }
            if (e==null && c==null) {
                return 3;
            }
        }
        return 0;
    }

    @Override
    public int checkLogin(AuthenticationRequest request) {
        int var = checkUsername(request.getUsername());

        if(var == 0) {
            return 0;
        } else if (var == 1) {
            Optional<Employee> e = employeeRepository.findByUsername(request.getUsername());
            if (e.get().getUsername().equals(request.getUsername()) &&
                    e.get().getPassword().equals(request.getPassword())
            ) {
                return 1;
            } else return 0;

        } else if(var == 2) {
            Optional<Customer> c = customerRepository.findByUsername(request.getUsername());
            if (c.get().getUsername().equals(request.getUsername()) &&
                    c.get().getPassword().equals(request.getPassword())
            ) {
                return 2;
            } else return 0;
        } else {
            Optional<Admin> a = adminRepository.findByUsername(request.getUsername());
            if (a.get().getUsername().equals(request.getUsername()) &&
                    a.get().getPassword().equals(request.getPassword())
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
}
