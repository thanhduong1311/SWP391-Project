package com.demo.homemate.controllers.customer;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.CustomerService;
import com.demo.homemate.services.UserService;
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

        return "";
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
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, ChangePasswordRequest request) {

        System.out.println(request.getUsername());
        System.out.println(request.getOldPassword());
        System.out.println(request.getNewPassword());
        System.out.println(request.getConfirmPassword());

        MessageOject messageOject = customerService.changePassword(request);

        System.out.println(messageOject.getMessage() + " 7&&&&%$&^$%^#$^#$%#$^%$^&$#^$#%^$%^%$#^$%^%$^#%$^#%$^$#%^#%$");

        return "redirect:/customer/account/changePassword";
    }
}
