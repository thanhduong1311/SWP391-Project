package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.feedback.FeedbackRequest;
import com.demo.homemate.dtos.notification.MessageObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICustomerService {

    MessageObject changePassword(ChangePasswordRequest request) ;
    FeedbackRequest getFeeback(int jobId);
    MessageObject feedback(FeedbackRequest fb, int customerID);
    public MessageObject editProfile(CustomerProfileRequest UserInfo,
                                     MultipartFile multipartFile,
                                     String foldername) throws IOException;
    public CustomerProfileRequest getProfile(String username);
}
