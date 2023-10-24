package com.demo.homemate.controllers.customer;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.mappings.CustomerMapping;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
        MessageOject messageOject = customerService.changePassword(request);
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

        CustomerProfileRequest cpr;
        CustomerMapping cp=new CustomerMapping();
        cpr = cp.toCustomerProfile(customerRepository.findByUsername(username));
        model.addAttribute("UserInfo",cpr);
        return "customer/customer-setting";
    }
    @PostMapping ("/edit")
    public String editProfile(CustomerProfileRequest UserInfo,
                              Model model){
        CustomerMapping cp= new CustomerMapping();
        Customer c =customerRepository.findByUsername(UserInfo.getUsername());
        c = cp.toCustomerFromCustomerProfile(c,UserInfo);
        customerRepository.save(c);
        return "redirect:/customer/account/" + UserInfo.getUsername();
    }

}
