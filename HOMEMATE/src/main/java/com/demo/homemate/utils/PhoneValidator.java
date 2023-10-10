package com.demo.homemate.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {

    private static final String PHONE_REGEX = "^((\\+84|84|0)[3|5|7|8|9])[0-9]{8}$";

    public static boolean isValid(String phone) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

}