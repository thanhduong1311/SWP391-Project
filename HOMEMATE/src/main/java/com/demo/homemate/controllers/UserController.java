package com.demo.homemate.controllers;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.AccountResponse;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.dtos.error.MessageOject;
import com.demo.homemate.services.AuthenticationService;
import com.demo.homemate.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {


    private final AuthenticationService authenticationService;

    private final UserService userService ;

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
        System.out.println("TOKEN: ");
        AuthenticationResponse auth = authenticationService.authentication(account);
        System.out.println("TOKEN: " +  auth.getToken());
        if(auth.getStateCode() == 1) {
            AccountResponse accountResponse = auth.getAccountResponse();
            // set data cho đối tượng được đăng nhập ,cái JWT nhét   đây nè mà chưa làm đượcz
            model.addAttribute("User",accountResponse);
            model.addAttribute("name",accountResponse.getName());
            Cookie cookie = new Cookie("Token", auth.getToken());
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
        }else {
            model.addAttribute("LoginError", new MessageOject("Fail","Username or password is incorrect!"));
            return loginView(model);
        }
        return "redirect:/viewpage";
    }



    /**
     * view của login
     * @param model
     * @return
     */
    @GetMapping("")
    public String loginView(Model model) {
        model.addAttribute("account", new AuthenticationRequest());
        if (model.getAttribute("LoginError") == null) {
            model.addAttribute("LoginError", new MessageOject("",""));
        }
        return "signin";
    }


    /**
     * view của home
     * @param model
     * @return
     */
    @GetMapping(value = "viewpage")
        public String viewHomePage(Model model,
                @CookieValue(name = "Token") String token) {
            JWTService jwt = new JWTService();
            model.getAttribute("User");
            if (token == null) {
                // The user is not logged in
                return "signup";
            }else {
                Claims claim = jwt.parseJwt(token);
                switch (claim.getSubject()) {
                    case "ADMIN" -> {
                        System.out.println("admin");
                        return "redirect:/admin";
                    }
                    case "CUSTOMER" -> {

                        return "redirect:/admin";
                    }
                    case "EMPLOYEE" -> {

                        return "redirect:/admin";
                    }
                    default -> {

                        return "login";
                    }
                }
            }
    }
    @GetMapping(value = "admin")
    public String viewAdminPage(){
        return "home";
    }
    @GetMapping(value = "customer")
    public String viewCustomerPage(){
        return "customer";
    }
    @GetMapping(value = "employee")
    public String viewEmployeePage(){
        return "employee";
    }



}
