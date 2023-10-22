package com.demo.homemate.utils;

public class PasswordValidate {
    public boolean checkPasswordValidate(String password) {
        if (password.length() >= 6) {
            return true;
        }
        return false;
    }
}