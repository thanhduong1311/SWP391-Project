package com.demo.homemate.data;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailContents {
    String subjectName;
    String title;
    String codeRecover;
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
                "<p>If you have any questions or requests, please contact us:</p>\n" +
                "<ul>\n" +
                "<li>Support Phone Number: 0813113149</li>\n" +
                "<li>Support Email: homematesuportteam@gmail.com</li>\n" +
                "</ul>\n" +
                "\n" +
                "<p>Thank you for signing up for Homemate.\nWe wish you a great experience!</p>\n" +
                "\n" +
                "<p>Sincerely,</p>\n" +
                "<p>Homemate</p>\n" +
                "</body>\n" +
                "</html>";
    }
    public String CreateRecoverPassword() {
        return "<html>\n" +
                "<head>\n" +
                "<title>"+this.title+"</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>Hi "+this.subjectName+",</p>\n" +
                "\n" +
                "<p>We have received a request to recover password!</p>\n" +
                "\n" +
                "<p>If it not you, please don't share this code for anyone</p>\n" +
                "\n" +
                "<p>The code: "+this.codeRecover+"</p>\n" +
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

    public String  EmployeeRegisterSucess() {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>Homemate Partner Registration Confirmation</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <p>Dear " +this.subjectName+",</p>\n" +
                "  <p>On behalf of Homemate, we would like to express our sincere gratitude for your interest in becoming our strategic partner.</p>\n" +
                "  <p>We have received your partner registration form and are currently reviewing it. We will be in touch with you as soon as possible with our decision.</p>\n" +
                "  <p>In the meantime, if you have any questions, please do not hesitate to contact us at:</p>\n" +
                "  <ul>\n" +
                "    <li>Email: <b>homematesuportteam@gmail.com</b></li>\n" +
                "    <li>Hotline: <b>0813113149</b></li>\n" +
                "  </ul>\n" +
                "  <p>Thank you for your registration.</p>\n" +
                "  <p>Sincerely,</p>\n" +
                "  <p>Homemate</p>\n" +
                "</body>\n" +
                "</html>";
    }

    public String  bookingSuccess() {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>Homemate Booking Confirmation</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <p>Dear " +this.subjectName+",</p>\n" +"<p>\n" +
                "        Your booking for Homemate has been confirmed.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        We will send you a notification when a employee has been assigned.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        Thank you for using Homemate!\n" +
                "    </p>"+
                "  <ul>\n" +
                "    <li>Email: <b>homematesuportteam@gmail.com</b></li>\n" +
                "    <li>Hotline: <b>0813113149</b></li>\n" +
                "  </ul>\n" +
                "  <p>Thank you for your booking.</p>\n" +
                "  <p>Sincerely,</p>\n" +
                "  <p>Homemate</p>\n" +
                "</body>\n" +
                "</html>";
    }

    public String  employeeReceive() {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>Homemate Booking Confirmation</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <p>Dear " +this.subjectName+",</p>\n" +"<p>\n" +
                "        Your booking for Homemate has been assigned.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        An employee has already accepted the job you requested, please check your booking history.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        Thank you for using Homemate!\n" +
                "    </p>"+
                "  <ul>\n" +
                "    <li>Email: <b>homematesuportteam@gmail.com</b></li>\n" +
                "    <li>Hotline: <b>0813113149</b></li>\n" +
                "  </ul>\n" +
                "  <p>Thank you for your booking.</p>\n" +
                "  <p>Sincerely,</p>\n" +
                "  <p>Homemate</p>\n" +
                "</body>\n" +
                "</html>";
    }

}
