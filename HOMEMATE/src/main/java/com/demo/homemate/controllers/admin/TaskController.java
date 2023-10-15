package com.demo.homemate.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tasks")
@RequiredArgsConstructor
public class TaskController {

    @GetMapping(value = "")
    public String viewTaskManagement(Model model) {
        return "admin/dashboard";
    }


}
