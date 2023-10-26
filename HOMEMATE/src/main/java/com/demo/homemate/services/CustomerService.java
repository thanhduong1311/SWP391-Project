package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.feedback.FeedbackRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Feedbacks;
import com.demo.homemate.entities.Job;
import com.demo.homemate.mappings.CustomerMapping;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.FeedbackRepository;
import com.demo.homemate.repositories.JobRepository;
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

    private final FeedbackRepository feedbackRepository;

    private final JobRepository jobRepository;



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

    @Override
    public FeedbackRequest getFeeback(int jobId) {
        FeedbackRequest fb = new FeedbackRequest();
        CustomerMapping cm = new CustomerMapping();
        try {
            if (feedbackRepository.findById(jobId)!=null){
                fb =cm.tofeedbackRequest(feedbackRepository.findById(jobId));
            }
            System.out.println(fb.getDetail());
            return fb;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fb;
    }

    @Override
    public MessageOject feedback(FeedbackRequest feedbackRequest, String username) {
    CustomerMapping customerMapping= new CustomerMapping();
    try {
        Customer customer = customerRepository.findByUsername(username);
        Job job = jobRepository.findById(feedbackRequest.getJobId());
        Feedbacks feedbacks = customerMapping.tofeedback(feedbackRequest, customer, job);
        feedbackRepository.save(feedbacks);
        MessageOject mo = new MessageOject("Success", "Save feedback", null);
        return mo;
    }catch(Exception e){
        System.out.println(e.getMessage());
    }

        return new MessageOject("Fail","Error save feedback",null);
    }
   /* public MessageOject editProfile(CustomerProfileRequest request) {
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

    }*/

}
