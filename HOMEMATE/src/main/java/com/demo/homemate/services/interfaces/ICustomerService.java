package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.feedback.FeedbackRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICustomerService {

    MessageOject changePassword(ChangePasswordRequest request) ;
    FeedbackRequest getFeeback(int jobId);
    MessageOject feedback(FeedbackRequest fb, int customerID);
    public MessageOject editProfile(CustomerProfileRequest UserInfo,
                                    MultipartFile multipartFile,
                                    String foldername) throws IOException;
    public CustomerProfileRequest getProfile(String username);
}
