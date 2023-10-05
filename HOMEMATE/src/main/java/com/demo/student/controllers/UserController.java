package com.demo.student.controllers;


import com.demo.student.dtos.auth.request.AuthenticationRequest;
import com.demo.student.entities.Account;
import com.demo.student.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private CustomerRepository accountRepository;

    @PostMapping(value = "/login")
    public String login(AuthenticationRequest account, Model model) {
        A
    }

    @GetMapping("")
    public String loginView(Model model) {
        model.addAttribute("account", new AuthenticationRequest());

        if (model.getAttribute("ErrorPass") == null) {
            model.addAttribute("ErrorPass", new AuthenticationRequest());

        }
        return "signin";
    }



}
