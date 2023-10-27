package com.demo.homemate.controllers.admin;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.enums.Role;
import com.demo.homemate.services.AdminService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminHomeController {

    private final AdminService adminService;

    @GetMapping(value = {"", "dashboard"})
    public String viewDashboard(Model model,
                                HttpServletRequest request,
                                HttpSession session,
                                @CookieValue(name = "Token",required = false) String cookieToken,
                                @SessionAttribute(value="SessionToken",required = false) String sessionToken
    ) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        JWTService jwt = new JWTService();
        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                if(claim.getSubject().equals(Role.ADMIN.toString())){
                    return "redirect:/admin/userManagement";
                }
                else return "redirect:/home";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else return "redirect:/login";
    }


}
