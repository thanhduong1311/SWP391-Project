package com.demo.homemate.controllers.admin;

import com.demo.homemate.dtos.homemateService.request.ServiceRequest;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;
import com.demo.homemate.entities.Service;
import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/add")
    public String viewAddService(Model model) {
        model.addAttribute("newService", new ServiceRequest());
        return "admin/addService";
    }

    @PostMapping("/add")
    public String addService(Model model, ServiceRequest request) {
        System.out.println(request.toString());
        ServiceResponse response = adminService.addService(request);
        System.out.println(response.getMessageOject().getMessage());
        System.out.println("===========================================");
        return "redirect:/admin/services";
    }



    @GetMapping("/edit/{id}")
    public String viewUpdateService(@PathVariable("id") int id, Model model) {
        model.addAttribute("newService", new ServiceRequest());
        return "admin/updateService";
    }

    @GetMapping("/detail/{id}")
    public String viewDetailService(@PathVariable("id") int id, Model model) {
        ServiceResponse response = adminService.detailService(id);
        model.addAttribute("ServiceDetail", response);
        return "admin/detailService";
    }





}
