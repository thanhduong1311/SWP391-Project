package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.mappings.CustomerMapping;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.interfaces.ICustomerService;
import com.demo.homemate.utils.PasswordMD5;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final UserService userService;

    private final CustomerRepository customerRepository;

    @SneakyThrows
    @Override
    public MessageOject changePassword(ChangePasswordRequest request) {
        try {
            String username = request.getUsername();
            String oldPas = request.getOldPassword();
            String newPasString= request.getNewPassword();
            String confirmPass = request.getConfirmPassword();

            int check = userService.checkNewChangePassword(username,oldPas,newPasString,confirmPass);

            if(check == 0 ) {
                return new MessageOject("Failed","Username is not exist",null);
            }
            if(check == 1 ) {
                return new MessageOject("Failed","Not allow password null here",null);
            }
            if(check == 2) {
                return new MessageOject("Failed","Old password is incorrect",null);
            }
            if(check == 3) {
                return new MessageOject("Failed","New password at least 6 character and include uppercase, lowercase and special characters",null);
            }
            if(check == 4) {
                return new MessageOject("Failed","New password is not match with confirm password",null);
            }
            if(check == 5) {
                Customer customer = customerRepository.findByUsername(username);
                customer.setPassword(PasswordMD5.encode(newPasString));
                customerRepository.save(customer);
                return new MessageOject("Success","Change password successfully",null);
            }
            return new MessageOject("Failed","Something went wrong when chaning password",null);
        } catch (Exception e) {
            return new MessageOject("Error",e.getMessage(),null);
        }

    }
    public MessageOject editProfile(CustomerProfileRequest request) {
        try {

            CustomerMapping cp= new CustomerMapping();
            Customer c =customerRepository.findByUsername(request.getUsername());
            c = cp.toCustomerFromCustomerProfile(c,request);
            customerRepository.save(c);

            int check = userService.checkNewChangePassword(username,oldPas,newPasString,confirmPass);

            if(check == 0 ) {
                return new MessageOject("Failed","Username is not exist",null);
            }
            if(check == 1 ) {
                return new MessageOject("Failed","Not allow password null here",null);
            }
            if(check == 2) {
                return new MessageOject("Failed","Old password is incorrect",null);
            }
            if(check == 3) {
                return new MessageOject("Failed","New password at least 6 character and include uppercase, lowercase and special characters",null);
            }
            if(check == 4) {
                return new MessageOject("Failed","New password is not match with confirm password",null);
            }
            if(check == 5) {
                Customer customer = customerRepository.findByUsername(username);
                customer.setPassword(PasswordMD5.encode(newPasString));
                customerRepository.save(customer);
                return new MessageOject("Success","Change password successfully",null);
            }
            return new MessageOject("Failed","Something went wrong when chaning password",null);
        } catch (Exception e) {
            return new MessageOject("Error",e.getMessage(),null);
        }

    }
}
