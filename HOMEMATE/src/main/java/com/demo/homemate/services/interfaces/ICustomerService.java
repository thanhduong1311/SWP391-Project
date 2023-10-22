package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.notification.MessageOject;

public interface ICustomerService {

    MessageOject changePassword(ChangePasswordRequest request) ;

}
