package com.demo.homemate.controllers.admin;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/userManagement")
@RequiredArgsConstructor
public class UserManagementController {

    private final AdminService adminService;

    @GetMapping()
    public String viewAccountManagement(Model model){
        List<Customer> customerList = adminService.getAllCustomer();
        List<Employee> employeeList = adminService.getAllEmployee();
        List<Employee> partnerList = adminService.getAllPartner();
        model.addAttribute("EmployeeList",employeeList);
        model.addAttribute("CustomerList",customerList);
        model.addAttribute("PartnerList",partnerList);
        return "admin/account-management";
    }

    @GetMapping("applies")
    public String viewAppliesList(Model model) {
        List<Employee> partnerList = adminService.getAllPartner();
        model.addAttribute("PartnerList",partnerList);
        return "admin/applyList";
    }

    @GetMapping("employees")
    public String viewCustomerList(Model model) {
        List<Employee> employeeList = adminService.getAllEmployee();

        model.addAttribute("EmployeeList",employeeList);
        return "admin/employeeList";
    }

    @GetMapping("customers")
    public String viewEmployeeList(Model model) {
        List<Customer> customerList = adminService.getAllCustomer();
        model.addAttribute("CustomerList",customerList);
        return "admin/customerList";
    }

}
