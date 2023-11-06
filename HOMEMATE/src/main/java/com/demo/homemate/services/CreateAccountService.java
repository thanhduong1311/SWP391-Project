package com.demo.homemate.services;

import com.demo.homemate.data.MailContents;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.email.EmailDetails;
import com.demo.homemate.dtos.employee.request.PartnerRegisterRequest;
import com.demo.homemate.dtos.employee.response.PartnerResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAccountService {

    private final UserService userService;


    public CustomerResponse createAccount(RegisterRequest request) {
        CustomerResponse response = new CustomerResponse();
        int check = 0; check= userService.createCustomer(request);
        if(check == 1) {
            response.setUsername(request.getUsername());
            response.setName(request.getLastName() + " " +request.getFirstName());
            response.setPhone(request.getPhone());
            response.setEmail(request.getEmail());
            response.setStateCode(1);

            MailContents mailContents = new MailContents();
            mailContents.setSubjectName(request.getLastName());
            mailContents.setTitle("[HOMEMATE] REGISTER ACCOUNT SUCCESSFULLY");


            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(request.getEmail());
            emailDetails.setSubject(mailContents.getTitle());
            emailDetails.setMsgBody(mailContents.CreateAccountSucess());

            response.setMessageOject(new MessageOject("Success", "Create account successfully!",emailDetails));

            return response;
        } else {
            if(check == 2) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "Username is early exist!",null));
            }

            if (check == 4) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "Phone number is invalid with Vietnamese phone number format!", null));
            }

            if(check == 3) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "Email is early exit!",null));
            }

            if(check == 5) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "New password is not match with confirm password!",null));
            }
            if(check == 6) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "Sorry, you age is not enough to register account",null));
            }
            return response;
        }
    }

    public PartnerResponse createAccount(PartnerRegisterRequest request) {
        PartnerResponse response = new PartnerResponse();
        int check = 0; check= userService.createEmployee(request);
        if(check == 1) {
            response.setUsername(request.getUsername());
            response.setName(request.getLastName() + " " +request.getFirstName());
            response.setPhone(request.getPhone());
            response.setEmail(request.getEmail());
            response.setStateCode(1);

            MailContents mailContents = new MailContents();
            mailContents.setSubjectName(request.getLastName());
            mailContents.setTitle("[HOMEMATE] PARTNER REGISTER SUCCESSFULLY");


            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(request.getEmail());
            emailDetails.setSubject(mailContents.getTitle());
            emailDetails.setMsgBody(mailContents.EmployeeRegisterSucess());

            response.setMessageOject(new MessageOject("Success", "Partner register successfully! Please wait for approve to start working!",emailDetails));

            return response;
        } else {
            if(check == 2) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "Username is early exist!",null));
            }

            if (check == 4) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "Phone number is invalid with Vietnamese phone number format!", null));
            }

            if(check == 3) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "New password is not match with confirm password!",null));
            }

            if(check == 5) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "Email is early exit!",null));
            }

            if(check == 6) {
                response.setStateCode(0);
                response.setMessageOject(new MessageOject("Failed", "ID Card number is early exit!",null));
            }
            return response;
        }
    }
}
