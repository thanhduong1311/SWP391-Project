package com.demo.homemate.controllers.admin;

import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.services.AdminService;
import jakarta.servlet.http.HttpSession;
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
    public String viewServiceManagement(Model model, HttpSession session){
        model.addAttribute("ServiceList", adminService.getAllService());
        MessageOject messageOject = new MessageOject();
        messageOject =(MessageOject) session.getAttribute("AddServiceMessage");
        session.removeAttribute("AddServiceMessage");
        model.addAttribute("AddServiceMessage",messageOject);

        messageOject =(MessageOject) session.getAttribute("UpdateServiceMessage");
        session.removeAttribute("UpdateServiceMessage");
        model.addAttribute("UpdateServiceMessage",messageOject);

        messageOject =(MessageOject) session.getAttribute("DeleteServiceMessage");
        session.removeAttribute("DeleteServiceMessage");
        model.addAttribute("DeleteServiceMessage",messageOject);

        messageOject =(MessageOject) session.getAttribute("DeleteServiceFailed");
        session.removeAttribute("DeleteServiceFailed");
        model.addAttribute("DeleteServiceFailed",messageOject);

        messageOject =(MessageOject) session.getAttribute("AddServiceFailed");
        session.removeAttribute("AddServiceFailed");
        model.addAttribute("AddServiceFailed",messageOject);

        messageOject =(MessageOject) session.getAttribute("UpdateServiceFailed");
        session.removeAttribute("UpdateServiceFailed");
        model.addAttribute("UpdateServiceFailed",messageOject);

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
                   @ModelAttribute("txtDetails") String details, HttpSession session) {
        MessageOject messageOject = adminService.addService(request,details);

        if(messageOject.getName() == "Success") {
            session.setAttribute("AddServiceMessage", new MessageOject("Success","",null));
        } else {
            session.setAttribute("AddServiceFailed", new MessageOject("Failed","",null));
        }

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
    public String viewEditlService(@PathVariable("id") int id, Model model,HttpSession session) {
        ServiceDetailResponse request = adminService.getAService(id);
        String joinedText = String.join("\n", request.getDetails());
        model.addAttribute("joinedText", joinedText);
        model.addAttribute("ServiceEdit", request);

        return "admin/UpdateService";
    }

    //Edit service handle
    @PostMapping("/edit")
    public String editlService(Model model,ServiceDetailResponse request,
                      @ModelAttribute("txtDetails") String details,HttpSession session) {
        MessageOject messageOject = adminService.updateService(request,details);

        System.out.println(messageOject.getMessage() + messageOject.getName());

        if(messageOject.getName() == "Success") {
            session.setAttribute("UpdateServiceMessage", new MessageOject("Success","",null));
        } else {
            session.setAttribute("UpdateServiceFailed", new MessageOject("Failed","",null));
        }
        return "redirect:/admin/services";
    }


    // Delete service handle
    @GetMapping("/delete/{id}")

    public String deleteService(@PathVariable("id") int id,HttpSession session) {
        MessageOject messageOject =  adminService.deleteService(id);
        System.out.println(messageOject.getName()+messageOject.getMessage());

        if(messageOject.getName() == "Success") {
            session.setAttribute("DeleteServiceMessage", new MessageOject("Success","Block account successfully",null));
        }else {
            session.setAttribute("DeleteServiceFailed", new MessageOject("Success","Block account successfully",null));
        }
        return "redirect:/admin/services";
    }


}
