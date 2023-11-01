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
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/employee/account")
@RequiredArgsConstructor
public class EmployeeAccountController {

    private final EmployeeRepository employeeRepository;

    private final EmployeeService employeeService;

    @GetMapping("")
    public String viewAccount( Model model,@CookieValue(name = "Token",required = false) String cookieToken,
                               @SessionAttribute(value="SessionToken",required = false) String sessionToken,
                               HttpSession session) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");
            EmployeMapping mapper = new EmployeMapping();


            EmployeeProlife prolife = mapper.toEmployeeProfile(employeeRepository.findByUsername(username));

            String s  = (String) session.getAttribute("EmployeeMessage");
            session.removeAttribute("EmployeeMessage");
            model.addAttribute("EmployeeMessage",s);

            model.addAttribute("profile",prolife);
            return "employee/Account";
    }


    @GetMapping("/{username}")
    public String viewProflie(@PathVariable("username") String username, Model model, HttpSession session) {

        EmployeMapping mapper = new EmployeMapping();


        EmployeeProlife prolife = mapper.toEmployeeProfile(employeeRepository.findByUsername(username));
        System.out.println("profile : " + prolife.toString());
        System.out.println("profile dob: " + employeeRepository.findByUsername(username).getDob());

        String s  = (String) session.getAttribute("EmployeeMessage");
        session.removeAttribute("EmployeeMessage");
        model.addAttribute("EmployeeMessage",s);

        model.addAttribute("profile",prolife);
        return "employee/profile";
    }

    @GetMapping("/changePassword")
    public String changePasswordView(Model model,@CookieValue(name = "Token",required = false) String cookieToken,
                                     @SessionAttribute(value="SessionToken",required = false) String sessionToken,HttpSession session) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");

        AccountMapper mapper = new AccountMapper();

        AccountResponse emp = mapper.toEmployeeResponse(employeeRepository.findByUsername(username));


        ChangePasswordRequest cr = new ChangePasswordRequest();
        cr.setUsername(emp.getUsername());

        String s  = (String) session.getAttribute("EmployeeMessage");
        session.removeAttribute("EmployeeMessage");
        model.addAttribute("EmployeeMessage",s);


        model.addAttribute("employee",emp);

        model.addAttribute("ChangePasswordRequest",cr);



        return "employee/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, ChangePasswordRequest request,HttpSession session) {

        System.out.println(request.getUsername());
        System.out.println(request.getOldPassword());
        System.out.println(request.getNewPassword());
        System.out.println(request.getConfirmPassword());

        MessageOject messageOject = employeeService.changePassword(request);

        session.setAttribute("EmployeeMessage", messageOject.getName()
                +"#" +messageOject.getMessage());

        System.out.println(messageOject.getMessage());
        return "redirect:/employee/account/changePassword";
    }

    @GetMapping ("/edit/{username}")
    public String viewEditProfile(@PathVariable("username") String username,
                                  Model model,
                                  HttpSession session,
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
        model.addAttribute("EmployeeMessage",session.getAttribute("EmployeeMessage"));
        session.removeAttribute("EmployeeMessage");
        model.addAttribute("employeeProfile", employeeProfile);
        return "employee/editProfile";
    }
    @PostMapping ("/edit")
    public String editProfile(EmployeeProlife request,
                              Model model,
                              @RequestParam("txtavatar") MultipartFile multipartFile,
                              HttpSession session
    ){
        System.out.println("++++++++++++++++++++++++++++++");
        MessageOject messageOject = employeeService.updateProfile(request,multipartFile,"employee");
        session.setAttribute("EmployeeMessage", messageOject.getName()
                +"#" +messageOject.getMessage());
        return "redirect:/employee/account/" + request.getUsername();
    }

}
