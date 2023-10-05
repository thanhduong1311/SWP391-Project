package com.demo.student.controllers;


import com.demo.student.entities.Account;
import com.demo.student.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private  AccountRepository accountRepository;

    @PostMapping(value = "/login")
    public String login(Account account, Model model) {
        Optional<Account> user = accountRepository.findByUsername(account.getUsername());
        if(user != null) {
            if(user.get().getUsername().equals(account.getUsername()) && user.get().getPassword().equals(account.getPassword()) ) {
                System.out.println("Login success");
                return "home";
            } else {
                model.addAttribute("ErrorPass", account);
                return loginView(model);
            }
        } else {
            return "index";
        }

    }

    @GetMapping("")
    public String loginView(Model model) {
        model.addAttribute("account", new Account());

        if (model.getAttribute("ErrorPass") == null) {
            model.addAttribute("ErrorPass", new Account());

        }
        return "signin";
    }



}
