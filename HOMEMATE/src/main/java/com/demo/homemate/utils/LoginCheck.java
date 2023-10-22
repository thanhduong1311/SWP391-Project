package com.demo.homemate.utils;

import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.UserService;
import lombok.RequiredArgsConstructor;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class LoginCheck {

public boolean add(int a,int b){
    UserService userService = null;
    return userService.checkLogin("giahuy","123");
}
}
