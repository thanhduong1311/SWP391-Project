package com.demo.homemate.controllers.admin;

import com.demo.homemate.dtos.notification.MessageObject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/userManagement")
@RequiredArgsConstructor
public class UserManagementController {

    private final AdminService adminService;


    // View account management view
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

    // View applies view
    @GetMapping("applies")
    public String viewAppliesList(Model model) {
        List<Employee> partnerList = adminService.getAllPartner();
        model.addAttribute("PartnerList",partnerList);
        return "admin/applyList";
    }

    // View employee list view
    @GetMapping("employees")
    public String viewEmployeeList(Model model) {
        List<Employee> employeeList = adminService.getAllEmployee();

        model.addAttribute("EmployeeList",employeeList);
        return "admin/employeeList";
    }

    // View customer list view
    @GetMapping("customers")
    public String viewCustomerList(Model model) {
        List<Customer> customerList = adminService.getAllCustomer();
        model.addAttribute("CustomerList",customerList);
        return "admin/customerList";
    }

    //View customer detail
    @GetMapping("/customer/{id}")
    public String customerDetail(@PathVariable("id") int id ,Model model) {
        Customer customer = adminService.getACustomer(id);
        model.addAttribute("customerDeatail",customer);
        return "/admin/customerDetail";
    }

    //View customer detail
    @GetMapping("/employee/{id}")
    public String employeeDetail(@PathVariable("id") int id ,Model model) {
        Employee employee = adminService.getAnEmployee(id);
        model.addAttribute("employeeDeatail",employee);
        return "/admin/employeeDetail";
    }

    //View customer detail
    @GetMapping("/partner/{id}")
    public String partnerDetail(@PathVariable("id") int id ,Model model) {
        Employee partner = adminService.getAnEmployee(id);
        model.addAttribute("partnerDeatail",partner);
        return "/admin/partnerDetail";
    }

    // Block customer handle
    @GetMapping("/customer/block/{id}")
    public String blockCustomer(@PathVariable("id") int id, Model model) {
        MessageObject messageObject = adminService.blockCustomer(id);
        return "redirect:/admin/userManagement/customer/"+id;
    }

    @GetMapping("/customer/unblock/{id}")
    public String unblockCustomer(@PathVariable("id") int id, Model model) {
        MessageObject messageObject = adminService.unBlockCustomer(id);
        return "redirect:/admin/userManagement/customer/"+id;
    }
    // delete customer handle
    @GetMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable("id") int id, Model model) {
        MessageObject messageObject = adminService.deleteCustomer(id);
        return "redirect:/admin/userManagement/customers";
    }

    // block employee
    @GetMapping("/employee/block/{id}")
    public String blockEmployee(@PathVariable("id") int id, Model model) {
        MessageObject messageObject = adminService.blockEmployee(id);
        return "redirect:/admin/userManagement/employee/"+id;
    }

    //unblock employee
    @GetMapping("/employee/unblock/{id}")
    public String unblockEmployee(@PathVariable("id") int id, Model model) {
        MessageObject messageObject = adminService.unBlockEmployee(id);
        return "redirect:/admin/userManagement/employee/"+id;
    }
    // delete customer handle
    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable("id") int id, Model model) {
        MessageObject messageObject = adminService.deleteEmployee(id);
        return "redirect:/admin/userManagement/employees";
    }

    @GetMapping("/employee/approve/{id}")
    public String approvePartner(@PathVariable("id") int id, Model model) {
        MessageObject messageObject = adminService.approvePartner(id);
        return "redirect:/admin/userManagement/applies";
    }

    @GetMapping("/employee/reject/{id}")
    public String rejectPartner(@PathVariable("id") int id, Model model) {
        //MessageObject messageOject = adminService.rejectPartner(id);
        return "redirect:/admin/userManagement/applies";
    }

}
