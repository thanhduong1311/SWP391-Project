package com.demo.homemate.controllers;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.AccountResponse;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.auth.response.AuthenticationResponse;
import com.demo.homemate.dtos.error.MessageOject;
import com.demo.homemate.services.AuthenticationService;
import com.demo.homemate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String login(AuthenticationRequest account, Model model) {
        AuthenticationResponse auth = authenticationService.authentication(account);
        System.out.println("TOKEN: " +  auth.getToken());
        if(auth.getStateCode() == 1) {
            AccountResponse accountResponse = auth.getAccountResponse();
            // set data cho đối tượng được đăng nhập ,cái JWT nhét   đây nè mà chưa làm được
            model.addAttribute("User",accountResponse);
            return viewHomePage(model);
        } else {
           model.addAttribute("LoginError", new MessageOject("Fail","Username or password is incorrect!"));
           return loginView(model);
        }

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
    @GetMapping(value = "/user/home")
    public String viewHomePage(Model model) {
        return "home";
    }



}
