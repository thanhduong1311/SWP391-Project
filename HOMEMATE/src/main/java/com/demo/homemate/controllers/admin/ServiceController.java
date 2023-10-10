package com.demo.homemate.controllers.admin;

import com.demo.homemate.entities.Service;
import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/services")
@RequiredArgsConstructor
public class ServiceController {

    private final AdminService adminService;

    @GetMapping(value = "")
    public String viewServiceManagement(Model model){
        List<Service> services = adminService.getAllService();
        model.addAttribute("ServiceList", services);
        return "admin/service-management";
    }


}
