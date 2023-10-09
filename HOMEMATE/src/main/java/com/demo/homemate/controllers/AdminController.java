package com.demo.homemate.controllers;


import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Service;
import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @GetMapping(value = "")
    public String viewDashboard(Model model){
        return "admin-dashboard";
    }

    @GetMapping(value = "/account-management")
    public String viewAccountManagement(Model model){
        List<Customer> customerList = adminService.getAllCustomer();
        List<Employee> employeeList = adminService.getAllEmployee();
        List<Employee> partnerList = adminService.getAllPartner();
        model.addAttribute("EmployeeList",employeeList);
        model.addAttribute("CustomerList",customerList);
        model.addAttribute("PartnerList",partnerList);
        return "admin-account-management";
    }


    @GetMapping(value = "/service-management")
    public String viewServiceManagement(Model model){
        List<Service> services = adminService.getAllService();
        model.addAttribute("ServiceList", services);
        return "admin-service-management";
    }

    @GetMapping(value = "/task-management")
    public String viewTaskManagement(Model model){
        return "admin-dashboard";
    }

}
