package com.demo.homemate.controllers.admin;

import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final AdminService adminService;

    @GetMapping(value = "")
    public String viewTaskManagement(Model model) {
        model.addAttribute("requestList", adminService.getRequestList());
        return "admin/taskManagement";
    }

    @GetMapping("/requests")
    public String viewRequest(Model model) {
        model.addAttribute("requestList", adminService.getRequestList());
        return "admin/employeeRequest";
    }
}
