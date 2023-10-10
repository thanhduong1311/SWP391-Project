package com.demo.homemate.controllers;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.error.MessageOject;
import com.demo.homemate.enums.Role;
import com.demo.homemate.services.interfaces.AuthenticationService;
import com.demo.homemate.services.CreateAccountService;
import com.demo.homemate.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final AuthenticationService authenticationService;

    private final CreateAccountService createAccountService;

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
        AuthenticationResponse auth = authenticationService.authentication(account);
        if(auth.getStateCode() == 1) {
            AccountResponse accountResponse = auth.getAccountResponse();
            model.addAttribute("User",accountResponse);
            model.addAttribute("name",accountResponse.getName());
            Cookie cookie = new Cookie("Token", auth.getToken());
            cookie.setMaxAge(60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }else {
            model.addAttribute("LoginError", new MessageOject("Fail","Username or password is incorrect!"));
            return loginView(model);
        }
        return "redirect:/user/home";
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
    @GetMapping(value = "/home")
        public String viewHomePage(Model model,
                @CookieValue(name = "Token") String token) {
            JWTService jwt = new JWTService();
            model.getAttribute("User");
            if (token == null) {
                // The user is not logged in
                return "signup";
            }else {
                Claims claim = jwt.parseJwt(token);
                System.out.println(claim);
                System.out.println((AccountResponse) claim.get("User"));
                switch (claim.getSubject()) {
                    case "ADMIN" -> {
                        return "redirect:/admin";
                    }
                    case "CUSTOMER" -> {
                        return "redirect:/user/customer";
                    }
                    case "EMPLOYEE" -> {
                        return "redirect:/user/employee";
                    }
                    default -> {
                        return "signin";
                    }
                }
            }
    }

    @GetMapping(value = "/customer")
    public String viewCustomerPage(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, "Token");
        String token=String.valueOf(cookie.getValue());
        JWTService jwt = new JWTService();

        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                System.out.println(claim.getSubject());
                if(claim.getSubject().equals(Role.CUSTOMER.toString())){
                    return "employee-home";
                }
                else return "redirect:/user/home";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else return "redirect:/user/home";
    }
    @GetMapping(value = "/employee")
    public String viewEmployeePage(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, "Token");
        String token=String.valueOf(cookie.getValue());
        JWTService jwt = new JWTService();

        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                System.out.println(claim.getSubject());
                if(claim.getSubject().equals(Role.EMPLOYEE.toString())){
                    return "employee-home";
                }
                else return "redirect:/user/home";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else return "redirect:/user/home";
    }


    @GetMapping("/signup")
    public String viewSignPage(Model model) {
        model.addAttribute("RequestRegister", new RegisterRequest());
        if (model.getAttribute("UserRegiter") == null) {
            model.addAttribute("UserRegiter", new MessageOject("",""));
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Model model, RegisterRequest request) {

        CustomerResponse response = createAccountService.createAccount(request);
        if(response.getStateCode() == 1) {

            model.addAttribute("UserRegiter", response.getMessageOject());
            System.out.println("OK");
            System.out.println(response.getMessageOject().getMessage());
            return "redirect:/user";
        }else {
            model.addAttribute("UserRegiter", response.getMessageOject());
            System.out.println("Okn't");
            System.out.println(response.getMessageOject().getMessage());
            return viewSignPage(model);

        }
    }

}
