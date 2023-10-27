package com.demo.homemate.controllers.common;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.employee.request.PartnerRegisterRequest;
import com.demo.homemate.dtos.employee.response.PartnerResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.dtos.password.RecoverPassword;
import com.demo.homemate.dtos.password.newPasswordRequest;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.CreateAccountService;
import com.demo.homemate.services.EmailService;
import com.demo.homemate.services.UserService;
import com.demo.homemate.services.interfaces.IAuthenticationService;
import com.demo.homemate.services.interfaces.IServiceService;
import com.demo.homemate.utils.PasswordValidate;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.NotPointcut;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticationService IAuthenticationService;

    private final CreateAccountService createAccountService;


    private final EmailService emailService;

    private final IServiceService serviceService;

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;
    private final CustomerRepository serviceRepository;

    private final UserService userService;

    /**
     * xử lí login
     * @param account
     * @param model
     * @return
     */


    @PostMapping(value = "/login")
    public String login(AuthenticationRequest account,
                        Model model,
                        HttpServletResponse response,
                        HttpSession session,
                        HttpServletRequest request,
                        @RequestParam(value="remember",required = false,defaultValue = "false") boolean rememberMe)
    {

    PasswordValidate loginValidate = new PasswordValidate();
    boolean check = loginValidate.checkPasswordValidate(account.getPassword());
    if(check == true) {
        AuthenticationResponse auth = IAuthenticationService.authentication(account);
        if(auth.getStateCode() == 1) {
            AccountResponse accountResponse = auth.getAccountResponse();
            model.addAttribute("User",accountResponse);
            Cookie cookie;

            cookie = new Cookie("Token", auth.getToken());
            if (rememberMe){
                session.removeAttribute("SessionToken");
                cookie.setMaxAge(60 * 60);
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/home";
            }else{
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                session.setAttribute("SessionToken",auth.getToken());

                return "redirect:/home";
            }
        }else {
//            session.removeAttribute("Login Failed");
            model.addAttribute("LoginMessage", new MessageOject("Failed","Username or password is incorrect!", null));
            return loginView(model);
        }
    } else {
        model.addAttribute("LoginMessage", new MessageOject("Failed","Password must contain at least 6 characters!", null));
        return loginView(model);
    }


    }


    /**
     * view của login
     * @param model
     * @return
     */
    @GetMapping("login")
    public String loginView(Model model) {
        model.addAttribute("account", new AuthenticationRequest());

        if (model.getAttribute("LoginMessage") == null) {
            model.addAttribute("LoginMessage", new MessageOject());
        }
        return "signin";
    }


    /**
     * view của home
     * @param model
     * @return
     */
    @GetMapping(value = "/home")
        public String viewHomePage(Model model,
                                   @CookieValue(name = "Token",required = false) String cookieToken,
                                   @SessionAttribute(value="SessionToken",required = false) String sessionToken,
                                   HttpSession session
                                   ){

        String s  = (String) session.getAttribute("SignupMessage");
        session.removeAttribute("SignupMessage");
        model.addAttribute("SignupMessage",s);

            model.getAttribute("User");
            if (cookieToken == null&& sessionToken==null) {
                return "/customer/home";
            }else {
                String token=cookieToken!=null?cookieToken:sessionToken;
                Claims claim = null;
                claim = JWTService.parseJwt(token);
                if (claim==null) {return "/login";}
                switch (claim.getSubject()) {
                    case "ADMIN" -> {
                        session.setAttribute("Message", new MessageOject("Success","Login Success",null));
                        return "redirect:/admin";
                    }
                    case "CUSTOMER" -> {
                        session.setAttribute("Message", new MessageOject("Success","Login Success",null));
                        return "redirect:/customer";
                    }
                    case "EMPLOYEE" -> {
                        session.setAttribute("LoginMessage", "Success#Login successfully");
                        return "redirect:/employee";
                    }
                    default -> {
                        return "redirect:/guest";
                    }
                }
            }
    }


    @GetMapping("/signup")
    public String viewSignPage(Model model,HttpSession session) {
        model.addAttribute("RequestRegister", new RegisterRequest());
        if (model.getAttribute("UserRegiter") == null) {
            model.addAttribute("UserRegiter", new MessageOject());
        }

        String s  = (String) session.getAttribute("SignupMessage");
        session.removeAttribute("SignupMessage");
        model.addAttribute("SignupMessage",s);

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Model model, RegisterRequest request, HttpSession session) {
        CustomerResponse response = createAccountService.createAccount(request);
        if(response.getStateCode() == 1) {
            emailService.sendEmail(response.getMessageOject().getEmailMessage());
            return "redirect:/login";
        }else {
            model.addAttribute("UserRegiter", response.getMessageOject());
            if(response.getMessageOject().getName() == "Success") {
                session.setAttribute("SignupMessage","Success#Register account successfully!");
            } else {
                String mes = response.getMessageOject().getName()+"#"+response.getMessageOject().getMessage();
                session.setAttribute("SignupMessage",mes);
            }
            return "redirect:/signup";
        }
    }

    @GetMapping("/guest")
    public String guestPage(Model model) {
        model.addAttribute("services", serviceService.getAllDetailServices());
        return "home";
    }
    @GetMapping("/services/{name}")
    public String getServiceDetail(Model model,
                                   @PathVariable("name") String name) {

        ServiceDetailResponse service = serviceService.getServiceByName(name);
        System.out.println(service.getName());
        System.out.println(service.getIntro());
        model.addAttribute("Service",service);
        return "serviceDetail";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         HttpServletRequest request,
                         HttpSession session ) {
        //Delete cookie

        Cookie cookie = WebUtils.getCookie(request, "Token");
        if (cookie!=null){
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        //Delete session
        session.removeAttribute("SessionToken");
        return "redirect:/login";
    }

    @GetMapping("/returnForgetpassword")
    public String viewRetypeEmail(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        session.removeAttribute("InputValue");
        session.removeAttribute("OTP");
        Cookie cookie = WebUtils.getCookie(request,"RToken");
        if (cookie!=null){
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/forgetpassword";
    }
    @GetMapping("/forgetpassword")
    public String viewForgetPassword(HttpServletRequest request,
                                     @CookieValue(name = "RToken",required = false) String rtoken,
                                     HttpSession session,
                                     Model model){
        MessageOject checkEmail = (MessageOject)session.getAttribute("EmailCheckMessage");
        session.removeAttribute("EmailCheckMessage");
        model.addAttribute("InputValue",session.getAttribute("InputValue"));
       if (rtoken!=null){
           if (checkEmail!=null) {
               model.addAttribute("Message","Success#Send email successfully");
           }
           if(session.getAttribute("MessageCheckOTP")!=null){
               MessageOject messageOject =(MessageOject)session.getAttribute("MessageCheckOTP");
               if (Objects.equals(messageOject.getName(), "Failed")){
                   model.addAttribute("Message",messageOject.getName()+"#"+messageOject.getMessage());
                   session.removeAttribute("MessageCheckOTP");
               }else{
                   return "redirect:/changepassword";
               }
           }
           session.setAttribute("OTP","Yes");
           model.addAttribute("OTP","Yes");
       }else{
           if (checkEmail==null) {
               if (session.getAttribute("OTP")=="Yes"){
                   model.addAttribute("Message","Failed#Code is expired!\nPlease try again");
                   model.addAttribute("OTP","Yes");
                   return "forget-password";
               }
               model.addAttribute("InputValue",null);
           }
           else if (Objects.equals(checkEmail.getName(), "Failed")) {
               session.removeAttribute("InputValue");
               model.addAttribute("Message","Failed#This email is not registed yet!");
               session.setAttribute("OTP","No");
               model.addAttribute("OTP","No");
           }else{
           }
       }
        return "forget-password";
    }
    @PostMapping("/forgetpassword")
    public String checkToSendEmail(HttpServletResponse response,
                                   Model model,
                                   HttpSession session,
                                   @ModelAttribute("txtEmail") String email
                                 ) {
        if (email==null||email.isEmpty()){
            return "redirect:/forgetpassword";
        }
        session.setAttribute("InputValue",email);
       if (userService.checkEmail(email)!=0)//neu email ton tai trong he thong
            {
                RecoverPassword recover = userService.createCodeRecover(email);
                emailService.sendEmail(recover.getEmailDetails());
                Cookie cookie = new Cookie("RToken",recover.getToken());
                cookie.setMaxAge(recover.getExpiration());
                response.addCookie(cookie);
                session.setAttribute("OTPSended","Success");
                session.setAttribute("EmailCheckMessage",new MessageOject("Success",email,null));
            }
       else{session.setAttribute("EmailCheckMessage",new MessageOject("Failed",email,null));}
        return "redirect:/forgetpassword";
    }
    @PostMapping("/checkCode")
    public String checkForgetPasswordCode(Model model,
                                          HttpSession session,
                                          @CookieValue(name = "RToken",required = false) String rtoken,
                                          @RequestParam(value="txtCode",required = false) String code,
                                          @RequestParam(value="txtEmail",required = false) String emailTxt
    ){
        if (rtoken == null) {
            return "redirect:/forgetpassword";
        }
        String email = String.valueOf(session.getAttribute("InputValue"));
        if (email==null) email= emailTxt;
       MessageOject mo = userService.checkOTPEmail(rtoken,code,email);
       session.setAttribute("MessageCheckOTP",mo);
        return "redirect:/forgetpassword";
    }
    @GetMapping("/changepassword")
    public String changePassword(Model model,
                                 HttpServletRequest request,
                                 newPasswordRequest newPass){
        if (newPass==null){
            model.addAttribute("newPass",new newPasswordRequest());
        }else{
            model.addAttribute("newPass",newPass);
        }

return "new-password";
    }
    @PostMapping("/changepassword")
    public String checkChangePassword(Model model,
                                      HttpSession session,
                                      HttpServletRequest request,
                                      newPasswordRequest newPass) throws NoSuchAlgorithmException {
        if (newPass.getNewPassword().equals(newPass.getRenewPassword())){
            String email =(String) session.getAttribute("EmailValue");
            userService.ChangePassword(email, newPass.getRenewPassword());
                return "redirect:/login";
        }else{

            newPass.setMessage("Password not matching");
        }
        return changePassword(model,request,newPass);
    }

    @GetMapping("/partnerRegister")
    public  String viewPartnerRegister(Model model) {
        model.addAttribute("service", serviceService.getAllServices());
        model.addAttribute("partnerInfo",new PartnerRegisterRequest());
        return "partnerRegister";
    }

    @PostMapping("/partnerRegister")
    public String partnerRegister(Model model, PartnerRegisterRequest request) {
        System.out.println(request.toString());
        PartnerResponse response = createAccountService.createAccount(request);
        if(response.getStateCode() == 1) {
            model.addAttribute("PartnerRegiter", response.getMessageOject());
            emailService.sendEmail(response.getMessageOject().getEmailMessage());
            System.out.println(response.getMessageOject().getMessage());
            System.out.println("********************************************************************************** SUCCESS");
            return "redirect:/guest";
        }else {
            model.addAttribute("PartnerRegiter", response.getMessageOject().getMessage());
            System.out.println(response.getMessageOject());
            System.out.println("********************************************************************************** FAILED");
            return viewPartnerRegister(model);
        }
    }

}
