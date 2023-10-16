package com.demo.homemate.data;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailContents {
    String subjectName;
    String title;
//    String createAccountSucess ;
//    String bookingSuccess;
//    String employeeRegisterSuccess;
//    String becomeEmployeeSuccess;
//    String cancelSucces;
//    String thankForReport;

    public String  CreateAccountSucess() {
        return "<html>\n" +
                "<head>\n" +
                "<title>Welcome to Homemate!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>Hi "+this.subjectName+",</p>\n" +
                "\n" +
                "<p>We are so excited to welcome you to Homemate!</p>\n" +
                "\n" +
                "<p>We hope you find our service helpful and that it allows you to free up your time to enjoy life.</p>\n" +
                "\n" +
                "<p>With your account, you can easily book house cleaning, elderly care, and childcare services that meet your needs.</p>\n" +
                "\n" +
                "<p>Here are some instructions to help you get started with Homemate:</p>\n" +
                "\n" +
                "<ol>\n" +
                "<li>Visit the Homemate website or app to create an account and provide your personal information.</li>\n" +
                "<li>Create a service request that includes the type of work to be done, the time, and the location.</li>\n" +
                "<li>Select a service provider that meets your needs.</li>\n" +
                "<li>Pay the service fee.</li>\n" +
                "</ol>\n" +
                "\n" +
                "<p>If you have any questions or requests, please contact us via hotline <b>0813113149</b> or email <b>homematesuportteam@gmail.com</b> .</p>\n" +
                "\n" +
                "<p>Thank you for signing up for Homemate. We wish you a great experience!</p>\n" +
                "\n" +
                "<p>Sincerely,</p>\n" +
                "<p>Homemate</p>\n" +
                "</body>\n" +
                "</html>";
    }
    public String ForgetPassword() {
        return "<html>\n" +
                "<head>\n" +
                "<title>Code for confirm Recover password!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>Hi "+this.subjectName+",</p>\n" +
                "\n" +
                "<p>We have received a request to recover password!</p>\n" +
                "\n" +
                "<p>If it not you, please don't share this code for anyone</p>\n" +
                "\n" +
                "<p>The code: "+this.title+"</p>\n" +
                "\n" +
                "<p>If you have any questions or requests, please contact us via hotline <b>0813113149</b> or email <b>homematesuportteam@gmail.com</b> .</p>\n" +
                "\n" +
                "<p>Thank you for signing up for Homemate. We wish you a great experience!</p>\n" +
                "\n" +
                "<p>Sincerely,</p>\n" +
                "<p>Homemate</p>\n" +
                "</body>\n" +
                "</html>";
    }

}
