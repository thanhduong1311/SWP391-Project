package com.demo.homemate.controllers.admin;

import com.demo.homemate.dtos.homemateService.request.ServiceRequest;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Service;
import com.demo.homemate.mappings.ServiceMapper;
import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/admin/services";
    }



    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable("id") int id) {
       MessageOject messageOject =  adminService.deleteService(id);

        return "redirect:/admin/services";
    }

    @GetMapping("/detail/{id}")
    public String viewDetailService(@PathVariable("id") int id, Model model) {
        Service response = adminService.getAService(id);
        model.addAttribute("ServiceDetail", response);
        return "admin/detailService";
    }

    @GetMapping("/edit/{id}")
    public String viewEditlService(@PathVariable("id") int id, Model model) {
        Service response = adminService.getAService(id);
        ServiceRequest request = new ServiceMapper().toServiceRequest(response);
        model.addAttribute("ServiceEdit", request);
        return "admin/updateService";
    }

    @PostMapping("/edit/{id}")
    public String editlService(@PathVariable("id") int id, Model model,ServiceRequest request) {
        MessageOject messageOject = adminService.updateService(request);
        return "redirect:/admin/services";
    }







}
