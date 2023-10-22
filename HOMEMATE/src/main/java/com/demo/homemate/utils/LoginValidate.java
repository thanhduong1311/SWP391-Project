package com.demo.homemate.utils;

import com.demo.homemate.dtos.notification.MessageOject;

public class LoginValidate {
    public MessageOject validateLogin(String username, String password) {

        if(password.length() <6) {
            return new MessageOject("Fail", "Password must contain at leats 6 characters!", null);
        }
        return new MessageOject(null,null,null);
    }
}
