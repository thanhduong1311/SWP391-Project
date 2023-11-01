package com.demo.homemate.controllers.employee;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.enums.Role;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.ServiceService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeHomeController {

    private final ServiceService serviceService;
    private final EmployeeRepository employeeRepository;

    @GetMapping({"", "home"})
    public String viewEmployeePage(Model model,
                                   HttpServletRequest request,
                                   @CookieValue(name = "Token",required = false) String cookieToken,
                                   @SessionAttribute(value="SessionToken",required = false) String sessionToken,
                                   HttpSession session
    ){

        String s  = (String) session.getAttribute("LoginMessage");
        session.removeAttribute("LoginMessage");
        model.addAttribute("EmployeeMessage",s);

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;
        JWTService jwt = new JWTService();

        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                System.out.println(claim.getSubject());
                if(claim.getSubject().equals(Role.EMPLOYEE.toString())){
                    String username = (String)claim.get("Username");
                    AccountResponse employee = new AccountMapper().toEmployeeResponse(employeeRepository.findByUsername(username));
                    //  List all services
                    model.addAttribute("AccountInfo",employee);
                    model.addAttribute("services", serviceService.getAllDetailServices());
                    return "/employee/home";
                }
                else return "redirect:/home";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else return "redirect:/employee/home";
    }
    @GetMapping("/services/{name}")
    public String getServiceDetail(Model model,
                                   @PathVariable("name") String name) {

        ServiceDetailResponse service = serviceService.getServiceByName(name);
        System.out.println(service.getName());
        System.out.println(service.getIntro());
        model.addAttribute("Service",service);
        return "employee/serviceDetail";
    }

}
