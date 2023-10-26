package com.demo.homemate.controllers.employee;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.employee.request.EmployeeProlifeEditRequest;
import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.mappings.AccountMapper;
import com.demo.homemate.mappings.CustomerMapping;
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
        System.out.println("profile : " + prolife.toString());
        System.out.println("profile dob: " + employeeRepository.findByUsername(username).getDob());

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

        EmployeMapping mapping = new EmployeMapping();
        EmployeeProlife employeeProfile = mapping.toEmployeeProfile(employeeRepository.findByUsername(uname));
        System.out.println(employeeProfile.toString() );
        model.addAttribute("employeeProfile", employeeProfile);
        return "employee/editProfile";
    }
    @PostMapping ("/edit")
    public String editProfile(Model model, EmployeeProlife request){

        System.out.println("Request: " + request.toString());

        MessageOject messageOject = employeeService.updateProfile(request);

        System.out.println(messageOject.getMessage());

        return "redirect:/employee/account/" + request.getUsername();
    }

}
