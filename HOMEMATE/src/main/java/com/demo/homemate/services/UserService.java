package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.enums.AccountStatus;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.interfaces.IUserService;
import com.demo.homemate.utils.PasswordMD5;
import com.demo.homemate.utils.PhoneValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;


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
        return -1;
    }

    @Override
    public int checkLogin(AuthenticationRequest request) {

        int var = checkUsername(request.getUsername());

        if (var == 0) {
            return 0;
        } else {
            PasswordMD5 p5 = new PasswordMD5();
            String password = "";
            try {
                password =p5.encode(request.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            if (var == 1) {
                Employee e = employeeRepository.findByUsername(request.getUsername());
                if (e.getUsername().equals(request.getUsername()) &&
                        e.getPassword().equals(password)
                ) {
                    return 1;
                } else return 0;

            } else if (var == 2) {
                Customer c = customerRepository.findByUsername(request.getUsername());
                if (c.getUsername().equals(request.getUsername()) &&
                        c.getPassword().equals(password)
                ) {
                    return 2;
                } else return 0;
            } else {
                Admin a = adminRepository.findByUsername(request.getUsername());
                if (a.getUsername().equals(request.getUsername()) &&
                        a.getPassword().equals(password)
                ) {
                    return 3;
                } else return 0;
            }
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

    @SneakyThrows
    @Override
    public int createCustomer(RegisterRequest request) {

        int checkPhone = checkPhone(request.getPhone());
        int checkEmail = checkEmail(request.getEmail());
        int checkNewPass = checkNewPassword(request.getPassword(), request.getConfirmPassword());
        int checkUsername = checkUsername(request.getUsername());


        if(checkPhone != 0 && checkEmail ==0 && checkNewPass !=0 && checkUsername ==0) {
            Customer customer = new Customer();
            customer.setUsername(request.getUsername());
            customer.setPassword(PasswordMD5.encode(request.getPassword()));
            customer.setFullName(request.getLastName() + " " +request.getFirstName());
            customer.setPhone(request.getPhone());
            customer.setEmail(request.getEmail());
            customer.setDob(request.getDob());
            customer.setRole(Role.CUSTOMER);
            customer.setAccountStatus(AccountStatus.ACTIVE);


            customerRepository.save(customer);

         return  customerRepository.findByUsername(request.getUsername()) != null ? 1:0;
        } else {
            if (checkUsername != 0) return 2;
            if (checkNewPass ==0 ) return 3;
            if  (checkPhone == 0) return 4;
            if (checkEmail != 0) return 5;
        }
            return 0;
    }

    @Override
    public int checkPhone(String phone) {
        return PhoneValidator.isValid(phone) == true ? 1:0;
    }

    @Override
    public int checkNewPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword) ? 1:0;
    }


}
