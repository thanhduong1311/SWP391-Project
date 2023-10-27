package com.demo.homemate.controllers.customer;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.image.ImageResponse;
import com.demo.homemate.dtos.notification.MessageObject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.mappings.CustomerMapping;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.CustomerService;
import com.demo.homemate.utils.UploadPicture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/customer/account")
@RequiredArgsConstructor
public class CustomerAccountController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @GetMapping("/{username}")
    public String viewAccount(@PathVariable("username") String username, Model model) {
        CustomerMapping cm = new CustomerMapping();
        CustomerProfileRequest profile =cm.toCustomerProfile(customerRepository.findByUsername(username));
        model.addAttribute("profile",profile);
        System.out.println(profile.getAvatar());
        return "customer/profile";
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
        MessageObject messageObject = customerService.changePassword(request);
        System.out.println(messageObject.getMessage());
        return "redirect:/customer/account/changePassword";
    }

    @GetMapping ("/edit/{username}")
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
        System.out.println(cpr.getDob());
        model.addAttribute("UserInfo",cpr);
        return "customer/customer-setting";
    }
    @PostMapping ("/edit")
    public String editProfile(CustomerProfileRequest UserInfo,
                              Model model,
                              @RequestParam("txtavatar") MultipartFile multipartFile) throws IOException {

        MessageObject messageObject = customerService.editProfile(UserInfo,multipartFile,"customer");
        return "redirect:/customer/account/" + UserInfo.getUsername();
    }

}
