package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.feedback.FeedbackRequest;
import com.demo.homemate.dtos.image.ImageResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Feedbacks;
import com.demo.homemate.entities.Job;
import com.demo.homemate.entities.Ranking;
import com.demo.homemate.mappings.CustomerMapping;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.FeedbackRepository;
import com.demo.homemate.repositories.JobRepository;
import com.demo.homemate.services.interfaces.ICustomerService;
import com.demo.homemate.utils.PasswordMD5;
import com.demo.homemate.utils.UploadPicture;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final UserService userService;

    private final CustomerRepository customerRepository;

    private final FeedbackRepository feedbackRepository;

    private final JobRepository jobRepository;
    private final RankingService rankingService;

    public Customer getCustomer(String username){
        return customerRepository.findByUsername(username);
    }
    public CustomerProfileRequest getProfile(String username){
        CustomerMapping customerMapping = new CustomerMapping();
        Customer customer = customerRepository.findByUsername(username);
        Ranking rank = rankingService.getRank(username);
        return customerMapping.toCustomerProfile(customer,rank);
    }
    public MessageOject editProfile(CustomerProfileRequest UserInfo,
                                    MultipartFile multipartFile,
                                    String foldername) throws IOException {
        try {
            UploadPicture uploadPicture = new UploadPicture();
            ImageResponse imageResponse = uploadPicture.uploadImage(multipartFile, foldername);
            CustomerMapping cp = new CustomerMapping();
            Customer c = customerRepository.findByUsername(UserInfo.getUsername());
            if (!imageResponse.getMessageOject().getName().equals("Error")) {
                c.setAvatar(imageResponse.getImgUrl());
            }
            c = cp.toCustomerFromCustomerProfile(c, UserInfo);
            customerRepository.save(c);
            return new MessageOject("Success","Edit profile successfully!\nInformation may take few second to load on!",null);
        }catch(Exception e){
            return new MessageOject("Failed","Fail to edit profile!",null);

        }
    }

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
    public FeedbackRequest getFeeback(int jobID) {
        FeedbackRequest fb = null;
        CustomerMapping cm = new CustomerMapping();
        try {
            Feedbacks feedbacks = feedbackRepository.findFeedbackByJobID(jobID);
            if (feedbacks!=null){
                fb =cm.tofeedbackRequest(feedbacks);
                return fb;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fb;
    }

    @Override
    public MessageOject feedback(FeedbackRequest feedbackRequest, int customerID) {
    CustomerMapping customerMapping= new CustomerMapping();
    try {
        Customer customer = customerRepository.findById(customerID);
        Job job = jobRepository.findById(feedbackRequest.getJobId());
        System.out.println(customer.getFullName()+job.getDescription());
        Feedbacks feedbacks = customerMapping.tofeedback(feedbackRequest, customer, job);
        System.out.println("feedback"+feedbacks.getFeedbackId());
        feedbackRepository.save(feedbacks);
        MessageOject mo = new MessageOject("Success", "Save feedback", null);
        return mo;
    }catch(Exception e){
        System.out.println(e.getMessage());
    }
        return new MessageOject("Failed","Error save feedback",null);
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
