package com.demo.homemate.services;

import com.demo.homemate.dtos.email.EmailDetails;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import javax.mail.Authenticator;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService {

    static final String from = "homematesuportteam@gmail.com";
    static final String password = "wasilomwbwhienvd";

    public static void main(String[] args) {
    EmailService e = new EmailService();
        e.sendEmail(new EmailDetails("thanhduongjnguyen@gmail.com","Thank you for using our service","HomeMate Say Hello to You",""));
        e.sendEmail(new EmailDetails("tuanla267@gmail.com","Thank you for using our service","HomeMate Say Hello to You",""));
        e.sendEmail(new EmailDetails("tuanlace170040@fpt.edu.vn","Thank you for using our service","HomeMate Say Hello to You",""));
    }

    @SneakyThrows
    public void sendEmail(EmailDetails emailDetails) {


        // Create authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password);
            }
        };
        // Create session
        Session session = Session.getInstance(createProperties(), auth);

        // Create message
        MimeMessage msg = createMessage(session, emailDetails.getRecipient(), emailDetails.getSubject(),emailDetails.getMsgBody());

        // Send message
        Transport.send(msg);
    }

    private static Properties createProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return props;
    }

    @SneakyThrows
    private static MimeMessage createMessage(Session session, String to, String subject, String mailContent) {
        MimeMessage msg = new MimeMessage(session);

        msg.addHeader("Content-type", "text;charset=UTF-8");
        msg.setFrom(from);
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parseHeader(to, false));
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        msg.setText(mailContent);

        return msg;
    }
}