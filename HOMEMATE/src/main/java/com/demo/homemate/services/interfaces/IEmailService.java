package com.demo.homemate.services.interfaces;

// Importing required classes

import com.demo.homemate.dtos.email.EmailDetails;

// Interface
public interface IEmailService {

    // Method
    // To send a simple email
    public String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    public  String sendMailWithAttachment(EmailDetails details);
}