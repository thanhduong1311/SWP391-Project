package com.demo.homemate.controllers.customer;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.services.interfaces.IAuthenticationService;
import com.demo.homemate.enums.Role;
import com.demo.homemate.services.CreateAccountService;
import com.demo.homemate.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerHomeController {

    private final IAuthenticationService IAuthenticationService;

    private final CreateAccountService createAccountService;

    private final UserService userService ;



    @GetMapping(value = {"", "home"})
    public String viewCustomerPage(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, "Token");
        if (cookie == null) {
            return "redirect:/login";
        }

        String token=String.valueOf(cookie.getValue());
        JWTService jwt = new JWTService();

        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                System.out.println(claim.getSubject());
                if(claim.getSubject().equals(Role.CUSTOMER.toString())){
                    return "employee/home";
                }
                else return "redirect:/customer/home";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else return "redirect:/customer/home";
    }


}
