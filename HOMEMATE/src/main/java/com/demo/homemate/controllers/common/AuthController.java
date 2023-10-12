package com.demo.homemate.controllers.common;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.services.CreateAccountService;
import com.demo.homemate.services.EmailService;
import com.demo.homemate.services.UserService;
import com.demo.homemate.services.interfaces.IAuthenticationService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticationService IAuthenticationService;

    private final CreateAccountService createAccountService;

    private final UserService userService ;

    @Autowired
    private final EmailService emailService;

    /**
     * xử lí login
     * @param account
     * @param model
     * @return
     */
    @PostMapping(value = "/login")
    public String login(AuthenticationRequest account,
                        Model model,
                        HttpServletResponse response) {
        AuthenticationResponse auth = IAuthenticationService.authentication(account);
        if(auth.getStateCode() == 1) {
            AccountResponse accountResponse = auth.getAccountResponse();
            model.addAttribute("User",accountResponse);
//            model.addAttribute("name",accountResponse.getName());
            Cookie cookie = new Cookie("Token", auth.getToken());
            cookie.setMaxAge(60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }else {
            model.addAttribute("LoginError", new MessageOject("Fail","Username or password is incorrect!", null));
            return loginView(model);
        }
        return "redirect:/home";
    }



    /**
     * view của login
     * @param model
     * @return
     */
    @GetMapping("login")
    public String loginView(Model model) {
        model.addAttribute("account", new AuthenticationRequest());
        if (model.getAttribute("LoginError") == null) {
            model.addAttribute("LoginError", new MessageOject());
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
                @CookieValue(name = "Token") String token) {
            JWTService jwt = new JWTService();

        System.out.println("=====================================" + jwt);

            model.getAttribute("User");
            if (token == null) {
                // The user is not logged in
                return "/customer/home";
            }else {
                Claims claim = jwt.parseJwt(token);
                System.out.println((AccountResponse) claim.get("User"));
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
                        return "signin";
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

            System.out.println(response.getMessageOject().getMessage());


            return "redirect:/login";
        }else {
            model.addAttribute("UserRegiter", response.getMessageOject());

            System.out.println(response.getMessageOject().getMessage());

            return viewSignPage(model);

        }
    }



}
