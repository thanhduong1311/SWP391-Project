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
import com.demo.homemate.entities.Service;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.security.NoSuchAlgorithmException;

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
            model.addAttribute("LoginMessage", new MessageOject("Fail","Username or password is incorrect!", null));
            return loginView(model);
        }
    } else {
        model.addAttribute("LoginMessage", new MessageOject("Fail","Password must contain at least 6 characters!", null));
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
                                   @SessionAttribute(value="SessionToken",required = false) String sessionToken
                                   ){
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
                        return "redirect:/admin";
                    }
                    case "CUSTOMER" -> {
                        return "redirect:/customer";
                    }
                    case "EMPLOYEE" -> {
                        return "redirect:/employee";
                    }
                    default -> {
                        return "redirect:/guest";
                    }
                }
            }
    }


    @GetMapping("/signup")
    public String viewSignPage(Model model) {
        model.addAttribute("RequestRegister", new RegisterRequest());
        if (model.getAttribute("UserRegiter") == null) {
            model.addAttribute("UserRegiter", new MessageOject());
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Model model, RegisterRequest request) {
        CustomerResponse response = createAccountService.createAccount(request);
        if(response.getStateCode() == 1) {
            model.addAttribute("UserRegiter", response.getMessageOject());
            emailService.sendEmail(response.getMessageOject().getEmailMessage());
            return "redirect:/login";
        }else {
            model.addAttribute("UserRegiter", response.getMessageOject());
            return viewSignPage(model);
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


    @GetMapping("/forgetpassword")
    public String showForgotpassword(HttpServletRequest request,
                                     HttpSession session,
                                     Model model){

        String message =(String) session.getAttribute("MessageError");
        String check = (String) session.getAttribute("checkResult");
        model.addAttribute("EmailSpace",session.getAttribute("EmailValue"));
        model.addAttribute("MessageError",message);

        if (WebUtils.getCookie(request,"RToken")!=null && check ==null){
            model.addAttribute("MessageSuccess","Send email success!Please check your email!");
            model.addAttribute("Confirm","OK");
        }
        else if (WebUtils.getCookie(request,"RToken")!=null && check !=null){
            if(check.equals("True")){
                return "redirect:/changepassword";
            }
            model.addAttribute("ErrorMessage",message);
            model.addAttribute("Message","");
            model.addAttribute("Confirm","OK");
        }else if(WebUtils.getCookie(request,"RToken")==null && message!=null){
            model.addAttribute("Confirm","Error");
            model.addAttribute("ErrorMessage","Code is expired. Please send email again!");
        }

        return "forget-password";
    }
    @PostMapping("/forgetpassword")
    public String forgetPassword(HttpServletResponse response,
                                 Model model,
                                 HttpSession session,
                                 @ModelAttribute("txtEmail") String email
                                 ) {

        if (email==null||email.isEmpty()){
            return "redirect:/forgetpassword";
        }
        session.setAttribute("EmailValue",email);
        System.out.println("===================================================");
        //Bam nut Send email hoac Confirm code
        //Neu co token (nhap code xong bam "Confirm"
       if (customerRepository.findByEmail(email)!=null||employeeRepository.findByEmail(email)!=null)
            {
                RecoverPassword recover = userService.createCodeRecover(email);
                emailService.sendEmail(recover.getEmailDetails());
                Cookie cookie = new Cookie("RToken",recover.getToken());
                cookie.setMaxAge(recover.getExpiration());
                response.addCookie(cookie);
                session.setAttribute("checkResult",null);
            }
       else{
               session.setAttribute("MessageError","This email did not exist!");
                }

        return "redirect:/forgetpassword";
    }
    @PostMapping("/checkCode")
    public String checkForgetPasswordCode(Model model,
                                          HttpSession session,
                                          @CookieValue(name = "RToken",required = false) String rtoken,
                                          @RequestParam(value="txtCode",required = false) String code
    ){
        if (rtoken == null) {
            session.setAttribute("MessageError","Code is expired. Please send email again!");
            return "redirect:/forgetpassword";
        }
        Claims claim = JWTService.parseJwt(rtoken);
        //neu co noi dung token
        if (claim!=null){
            String email =(String) session.getAttribute("EmailValue");
            String emailToken = claim.getSubject();
            String codeToken = (String) claim.get("tokenConfirm");

            //kiem tra emai vs code co dung vs token khong ( o email dc set ve readonly truoc do de nguoi dung khong thay doi dc
            if (emailToken.equals(email) && code.trim().equals(codeToken)){
                session.setAttribute("checkResult","True");
                return "redirect:/changepassword";
            }else {
                session.setAttribute("checkResult","Wrong");
                session.setAttribute("MessageError","Wrong code please try again!");
                return "redirect:/forgetpassword";
            }
        }
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
