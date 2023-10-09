package com.demo.homemate.services;

import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.error.MessageOject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAccountService {

    @Autowired
    private UserService userService;
    public CustomerResponse createAccount(RegisterRequest request) {
        CustomerResponse response = new CustomerResponse();
        int check = 0; check= userService.createCustomer(request);
        if(check == 1) {
            response.setUsername(request.getUsername());
            response.setName(request.getLastName() + " " +request.getFirstName());
            response.setPhone(request.getPhone());
            response.setEmail(request.getEmail());
            response.setStateCode(1);
            response.setMessageOject(new MessageOject("Success", "Create account successfully!"));
            return response;
        } else {
            if(check == 2) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Fail", "Username is early exist!"));
            }

            if (check == 4) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Fail", "Phone number is invalid with Vietnamese phone number format!"));
            }

            if(check == 3) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Fail", "New password is not match with confirm password!"));
            }

            if(check == 5) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Fail", "Email is early exit!"));
            }
            return response;
        }
    }
}
