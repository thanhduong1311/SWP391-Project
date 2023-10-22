package com.demo.homemate.controllers.employee;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.mappings.EmployeMapping;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee/account")
@RequiredArgsConstructor
public class EmployeeAccountController {

    private final EmployeeRepository employeeRepository;

    private final EmployeeService employeeService;

    @GetMapping("/{username}")
    public String viewAccount(@PathVariable("username") String username, Model model) {

        EmployeMapping mapper = new EmployeMapping();

        EmployeeProlife prolife = mapper.toEmployeeProfile(employeeRepository.findByUsername(username));
        model.addAttribute("profile",prolife);
        return "employee/profile";
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

        AccountResponse emp = mapper.toEmployeeResponse(employeeRepository.findByUsername(username));


        ChangePasswordRequest cr = new ChangePasswordRequest();
        cr.setUsername(emp.getUsername());


        model.addAttribute("employee",emp);

        model.addAttribute("ChangePasswordRequest",cr);
        return "employee/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, ChangePasswordRequest request) {

        System.out.println(request.getUsername());
        System.out.println(request.getOldPassword());
        System.out.println(request.getNewPassword());
        System.out.println(request.getConfirmPassword());

        MessageOject messageOject = employeeService.changePassword(request);

        System.out.println(messageOject.getMessage());
        return "redirect:/employee/account/" + request.getUsername();
    }

}
