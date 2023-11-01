package com.demo.homemate.controllers.customer;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Member;
import com.demo.homemate.entities.Ranking;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.mappings.CustomerMapping;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.CustomerService;

import com.demo.homemate.services.RankingService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/customer/account")
@RequiredArgsConstructor
public class CustomerAccountController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final RankingService rankingService;

    @GetMapping("")
    public String viewAccount( Model model,
                              HttpSession session,@CookieValue(name = "Token",required = false) String cookieToken,
                               @SessionAttribute(value="SessionToken",required = false) String sessionToken) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");
        Ranking rank = rankingService.getRank(username);
        CustomerMapping cm = new CustomerMapping();
        CustomerProfileRequest profile =cm.toCustomerProfile(customerRepository.findByUsername(username),rank);
        model.addAttribute("profile",profile);


        return "customer/Account";
    }

    @GetMapping("/{username}")
    public String viewProfile(@PathVariable("username") String username,
                              Model model,
                              HttpSession session) {
        CustomerMapping cm = new CustomerMapping();
        CustomerProfileRequest profile =customerService.getProfile(username);
        model.addAttribute("profile",profile);
        Ranking ranking = rankingService.getRank(username);
        if (ranking!=null){
            model.addAttribute("Ranking",ranking.getName());
        }
        Customer c = customerRepository.findByUsername(username);
        MessageOject mo = rankingService.checkRank(c);
        MessageOject messageOject = (MessageOject)session.getAttribute("EditMessage");
        session.removeAttribute("EditMessage");
        if (messageOject!=null){
            if (Objects.equals(messageOject.getName(), "Success")){
                model.addAttribute("EditMessage22","Success#Edit profile successfully!");
            }else{
                model.addAttribute("EditMessage1","Failed#Fail to edit profile!");
            }
        }model.addAttribute("EditMessage","Success#Login successfully");
        return "customer/customer-profile";
    }
    @GetMapping("/changePassword")
    public String changePasswordView(Model model,@CookieValue(name = "Token",required = false) String cookieToken,
                                     @SessionAttribute(value="SessionToken",required = false) String sessionToken) {
        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;
        String username = (String) JWTService.parseJwt(token).get("Username");
        AccountMapper mapper = new AccountMapper();
        AccountResponse customerResponse = mapper.toCustomerResponse(customerRepository.findByUsername(username));
        ChangePasswordRequest cr = new ChangePasswordRequest();
        cr.setUsername(customerResponse.getUsername());
        model.addAttribute("customer",customerResponse);
        model.addAttribute("ChangePasswordRequest",cr);
        return "customer/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, ChangePasswordRequest request) {
        MessageOject messageOject = customerService.changePassword(request);
        System.out.println(messageOject.getMessage());
        return "redirect:/customer/account/changePassword";
    }

    @RequestMapping ("/edit/{username}")
    public String viewEditProfile(@PathVariable("username") String username,
                                  Model model,
                                  @CookieValue(name = "Token",required = false) String cookieToken,
                                  @SessionAttribute(value="SessionToken",required = false) String sessionToken
    ){
        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;
        String uname = (String) JWTService.parseJwt(token).get("Username");
        if (!username.equals(uname)){
            return "error";
        }
        CustomerProfileRequest cpr = customerService.getProfile(username);
        model.addAttribute("UserInfo",cpr);
        return "customer/customer-setting";
    }
    @PostMapping ("/edit")
    public String editProfile(CustomerProfileRequest UserInfo,
                              Model model,
                              @RequestParam("txtavatar") MultipartFile multipartFile,
                              HttpSession session
                              ) throws IOException {
        MessageOject messageOject = customerService.editProfile(UserInfo,multipartFile,"customer");
        session.setAttribute("EditMessage",messageOject);
        return "redirect:/customer/account/" + UserInfo.getUsername();
    }
    @GetMapping("{username}/reward")
    public String viewReward( @PathVariable("username") String username,
                              Model model){
        System.out.println(username);
        CustomerProfileRequest cpr = customerService.getProfile(username);
        model.addAttribute("profile",cpr);
        List<Ranking> listrank = rankingService.getRanks();
        model.addAttribute("listRank",listrank);
        return "customer/customer-reward";
    }
   /* @GetMapping("/rank")
    public String viewRank() {

        *//*Ranking rank = rankingService.getRank(customerRepository.findById(1));
        System.out.println(rank.toString() + " absdjhbaskdhbasyudg678wygdukjashgdiastd678yuasgudkjahgsydgasiudas");
        return "";*//*
    }*/
}
