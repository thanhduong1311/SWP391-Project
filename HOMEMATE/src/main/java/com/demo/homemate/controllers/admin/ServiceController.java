package com.demo.homemate.controllers.admin;

import com.demo.homemate.dtos.notification.MessageObject;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/services")
@RequiredArgsConstructor
public class ServiceController {

    private final AdminService adminService;

    //View service management page;
    @GetMapping(value = "")
    public String viewServiceManagement(Model model){
        model.addAttribute("ServiceList", adminService.getAllService());
        return "admin/service-management";
    }

    // View add service
    @GetMapping("/add")
    public String viewAddService(Model model) {
        model.addAttribute("newService", new ServiceDetailResponse());
        return "admin/addService";
    }

    // Add service handle
    @PostMapping("/add")
    public String addService(Model model, ServiceDetailResponse request,
                             @ModelAttribute("txtDetails") String details) {
        MessageObject messageObject = adminService.addService(request,details);
        System.out.println(messageObject.getName()+ messageObject.getMessage());
        return "redirect:/admin/services";
    }

    // Details service view
    @GetMapping("/detail/{id}")
    public String viewDetailService(@PathVariable("id") int id, Model model) {
        ServiceDetailResponse response = adminService.getAService(id);
        model.addAttribute("ServiceDetail", response);
        return "admin/detailService";
    }



    // Edit service View
    @GetMapping("/edit/{id}")
    public String viewEditlService(@PathVariable("id") int id, Model model) {
        ServiceDetailResponse request = adminService.getAService(id);
        String joinedText = String.join("\n", request.getDetails());
        model.addAttribute("joinedText", joinedText);
        model.addAttribute("ServiceEdit", request);
        return "admin/UpdateService";
    }

    //Edit service handle
    @PostMapping("/edit")
    public String editlService(Model model,ServiceDetailResponse request,
                               @ModelAttribute("txtDetails") String details) {
        MessageObject messageObject = adminService.updateService(request,details);
        System.out.println(messageObject.getName()+ messageObject.getMessage());
        return "redirect:/admin/services";
    }


    // Delete service handle
    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable("id") int id) {
        MessageObject messageObject =  adminService.deleteService(id);
        System.out.println(messageObject.getName()+ messageObject.getMessage());
        return "redirect:/admin/services";
    }


}
