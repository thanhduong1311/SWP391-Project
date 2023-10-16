package com.demo.homemate.controllers.common;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.CreateAccountService;
import com.demo.homemate.services.EmailService;
import com.demo.homemate.services.UserService;
import com.demo.homemate.services.interfaces.IAuthenticationService;
import com.demo.homemate.services.interfaces.IServiceService;
import com.sun.net.httpserver.HttpContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserService userService ;

    private final EmailService emailService;

    private final IServiceService serviceService;
    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

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
    public String showForgotpassword(){
        return "forget-password";
    }
    @PostMapping("/forgetpassword")
    public String forgetPassword(HttpServletResponse response,
                                 Model model,
                                 @RequestParam(value="txtEmail") String email)
    {
        if (email==null||email.isEmpty()){
            return "redirect:/forgetpassword";
        }

        if (customerRepository.findByEmail(email)!=null||employeeRepository.findByEmail(email)!=null)
        {
            return "home";
        }else{
            return "redirect:/forgetpassword";
        }

    }

    @GetMapping("/guest/partnerRegister")
    public  String viewPartnerRegister(Model model) {
            return "partnerRegister";
    }

}
