package com.demo.homemate.controllers.customer;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.interfaces.IAuthenticationService;
import com.demo.homemate.enums.Role;
import com.demo.homemate.services.CreateAccountService;
import com.demo.homemate.services.UserService;
import com.demo.homemate.services.interfaces.IServiceService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerHomeController {

    private final IAuthenticationService IAuthenticationService;

    private final CreateAccountService createAccountService;

    private final UserService userService ;

    private final IServiceService serviceService;
    private final CustomerRepository customerRepository;

    @GetMapping(value = {"","/home"})
    public String viewCustomerPage(Model model,
                                   HttpSession session,
                                   HttpServletRequest request,
                                   @CookieValue(name = "Token",required = false) String cookieToken,
                                   @SessionAttribute(value="SessionToken",required = false) String sessionToken
    ){
        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;
        JWTService jwt = new JWTService();

        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                if(claim.getSubject().equals(Role.CUSTOMER.toString())){

                    String username = (String)claim.get("Username");
                    AccountResponse customer = new AccountMapper().toCustomerResponse(customerRepository.findByUsername(username));
                   //  List all services
                    String message =(String)(session.getAttribute("CustomerMessage"));
                    session.removeAttribute("CustomerMessage");
                    model.addAttribute("CustomerMessage", message);


                    model.addAttribute("services", serviceService.getAllDetailServices());
                    model.addAttribute("AccountInfo",customer);
                    return "customer/customer-home";
                }
                else return "redirect:/home";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else return "redirect:/home";
    }



    @GetMapping("/services/{id}")
    public String getServiceDetail(Model model,
                                   @PathVariable("id") int id) {

        ServiceDetailResponse service = serviceService.getServiceByID(id);
        System.out.println(service.getName());
        System.out.println(service.getIntro());
        model.addAttribute("Service",service);
        return "customer/serviceDetail";
    }

}
