package com.demo.homemate.controllers.common;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.employee.request.PartnerRegisterRequest;
import com.demo.homemate.dtos.employee.response.PartnerResponse;
import com.demo.homemate.dtos.email.EmailDetails;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.dtos.password.RecoverPassword;
import com.demo.homemate.dtos.password.tokenEmailConfirm;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.CreateAccountService;
import com.demo.homemate.services.EmailService;
import com.demo.homemate.services.UserService;
import com.demo.homemate.services.interfaces.IAuthenticationService;
import com.demo.homemate.services.interfaces.IServiceService;
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

        AuthenticationResponse auth = IAuthenticationService.authentication(account);
        if(auth.getStateCode() == 1) {
            AccountResponse accountResponse = auth.getAccountResponse();
            model.addAttribute("User",accountResponse);
            Cookie cookie;

            cookie = new Cookie("Token", auth.getToken());
            if (rememberMe){
                System.out.println("Trước đó: "+session.getAttribute("SessionToken"));
                session.removeAttribute("SessionToken");
                System.out.println("Sau đó: "+session.getAttribute("SessionToken"));

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
            model.addAttribute("LoginMessage", new MessageOject("Fail","Username or password is incorrect!", null));
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
                String v = null;
                Claims claim=null;
                if (cookieToken!=null){
                    claim = JWTService.parseJwt(cookieToken);
                    System.out.println("token"+cookieToken);
                }
                else if(sessionToken!= null) {
                    claim = JWTService.parseJwt(sessionToken);
                    System.out.println("session"+sessionToken);
                }
                System.out.println(claim.getSubject());
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
        model.addAttribute("services", serviceService.getAllServices());
        return "home";
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
    public String showForgotpassword(HttpServletRequest request, Model model){
        Cookie cookie = WebUtils.getCookie(request,"RToken");
        System.out.println("Co model khong: "+ model.getAttribute("ConfirmedEmail"));
        if (cookie!=null)
        model.addAttribute("Confirm","huy@gmail.com");
        else model.addAttribute("Confirm","");
        return "forget-password";
    }
    @PostMapping("/forgetpassword")
    public String forgetPassword(HttpServletResponse response,
                                 HttpServletRequest request,
                                 Model model,
                                 @RequestParam(value="txtEmail") String email,
                                 @RequestParam(value="txtCode",required = false) String code,
                                 @CookieValue(name = "RToken",required = false) String rtoken
                                 ) {
        if (email==null||email.isEmpty()){
            return "redirect:/forgetpassword";
        }
        //Bam nut Send email hoac Confirm code
        //Neu co token (nhap code xong bam "Confirm"
        if (rtoken!=null){
            Claims claim = JWTService.parseJwt(rtoken);
            //neu co noi dung token
            if (claim!=null){
                tokenEmailConfirm tokenEConfirm = (tokenEmailConfirm)claim.get("Recover");
                //kiem tra emai vs code co dung vs token khong ( o email dc set ve readonly truoc do de nguoi dung khong thay doi dc
                if (tokenEConfirm.getEmail().equals(email) && code.trim().equals(tokenEConfirm.getCode())){
                        //chuuen den trang nhap pass moi
                }else {
                    //sai code thi cut
                    model.addAttribute("WrongCode","Wrong code please try again!");
                    return "redirect:/forgetpassword";
                }
            }
            


        //Neu khong co token thi tao 1 token , giai doan Bam Send email/Resend email de no tao
            // VE SAU TACH RA LM FUNCTION RIENG, DE THEM TRG HOP HET HAN COOKIE( HOAC TOKEN - CHUA XEM)
        }else{
        if (customerRepository.findByEmail(email)!=null||employeeRepository.findByEmail(email)!=null)
        {
            RecoverPassword recover = userService.createCodeRecover(email);
            model.addAttribute("ConfirmedEmail", email);
            emailService.sendEmail(recover.getEmailDetails());
            Cookie cookie = new Cookie("RToken",recover.getToken());
            cookie.setMaxAge(recover.getExpiration());
            response.addCookie(cookie);
        }
        }
        return "redirect:/forgetpassword";
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
