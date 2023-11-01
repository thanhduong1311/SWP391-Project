package com.demo.homemate.controllers.admin;

import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.services.AdminService;
import jakarta.servlet.http.HttpSession;
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
    public String viewAccountManagement(Model model, HttpSession session){

        if (model.getAttribute("loginSuccess") == null) {
            model.addAttribute("loginSuccess", new MessageOject("","",null));
        }




        String s  = (String) session.getAttribute("LoginMessage");
        session.removeAttribute("LoginMessage");
        model.addAttribute("LoginMessage",s);




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
    public String viewAppliesList(Model model,HttpSession session) {
        List<Employee> partnerList = adminService.getAllPartner();
        model.addAttribute("PartnerList",partnerList);

        MessageOject messageOject = new MessageOject();

        messageOject =(MessageOject) session.getAttribute("ApproveMessage");
        session.removeAttribute("ApproveMessage");
        model.addAttribute("ApproveMessage",messageOject);

        return "admin/applyList";
    }

    // View employee list view
    @GetMapping("employees")
    public String viewEmployeeList(Model model,HttpSession session) {
        List<Employee> employeeList = adminService.getAllEmployee();

        MessageOject messageOject = new MessageOject();
        messageOject =(MessageOject) session.getAttribute("DeleteAccountMessage");
        session.removeAttribute("DeleteAccountMessage");
        model.addAttribute("DeleteAccountMessage",messageOject);

        messageOject =(MessageOject) session.getAttribute("ApproveMessage");
        session.removeAttribute("ApproveMessage");
        model.addAttribute("ApproveMessage",messageOject);

        messageOject =(MessageOject) session.getAttribute("DeleteAccountFailed");
        session.removeAttribute("DeleteAccountFailed");
        model.addAttribute("DeleteAccountFailed",messageOject);

        model.addAttribute("EmployeeList",employeeList);
        return "admin/employeeList";
    }

    // View customer list view
    @GetMapping("customers")
    public String viewCustomerList(Model model,HttpSession session) {
        List<Customer> customerList = adminService.getAllCustomer();
        model.addAttribute("CustomerList",customerList);

        MessageOject messageOject = new MessageOject();
        messageOject =(MessageOject) session.getAttribute("DeleteAccountMessage");
        session.removeAttribute("DeleteAccountMessage");
        model.addAttribute("DeleteAccountMessage",messageOject);

        messageOject =(MessageOject) session.getAttribute("DeleteAccountFailed");
        session.removeAttribute("DeleteAccountFailed");
        model.addAttribute("DeleteAccountFailed",messageOject);

        return "admin/customerList";
    }

    //View customer detail
    @GetMapping("/customer/{id}")
    public String customerDetail(@PathVariable("id") int id ,Model model,HttpSession session) {
        Customer customer = adminService.getACustomer(id);
        model.addAttribute("customerDeatail",customer);

        MessageOject messageOject = new MessageOject();
        messageOject =(MessageOject) session.getAttribute("LockAccountMessage");
        session.removeAttribute("LockAccountMessage");
        model.addAttribute("LockAccountMessage",messageOject);

       messageOject =(MessageOject) session.getAttribute("UnLockAccountMessage");
        session.removeAttribute("UnLockAccountMessage");
        model.addAttribute("UnLockAccountMessage",messageOject);


        return "/admin/customerDetail";
    }

    //View customer detail
    @GetMapping("/employee/{id}")
    public String employeeDetail(@PathVariable("id") int id ,Model model, HttpSession session) {
        Employee employee = adminService.getAnEmployee(id);
        model.addAttribute("employeeDeatail",employee);

        MessageOject messageOject = new MessageOject();
        messageOject =(MessageOject) session.getAttribute("LockAccountMessage");
        session.removeAttribute("LockAccountMessage");
        model.addAttribute("LockAccountMessage",messageOject);

        messageOject =(MessageOject) session.getAttribute("UnLockAccountMessage");
        session.removeAttribute("UnLockAccountMessage");
        model.addAttribute("UnLockAccountMessage",messageOject);

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

    public String blockCustomer(@PathVariable("id") int id, Model model,HttpSession session) {
        MessageOject messageOject = adminService.blockCustomer(id);
        System.out.println(messageOject.getMessage());
        session.setAttribute("LockAccountMessage", new MessageOject("Success","Block account successfully",null));
        return "redirect:/admin/userManagement/customer/"+id;
    }

    @GetMapping("/customer/unblock/{id}")
    public String unblockCustomer(@PathVariable("id") int id, Model model,HttpSession session) {
        MessageOject messageOject = adminService.unBlockCustomer(id);
        System.out.println(messageOject.getMessage());
        session.setAttribute("UnLockAccountMessage", new MessageOject("Success","Unblock account successfully",null));
        return "redirect:/admin/userManagement/customer/"+id;
    }
    // delete customer handle
    @GetMapping("/customer/delete/{id}")

    public String deleteCustomer(@PathVariable("id") int id, Model model,HttpSession session) {
        MessageOject messageOject = adminService.deleteCustomer(id);
        if(messageOject.getName() == "Success") {
            session.setAttribute("DeleteAccountMessage", new MessageOject("Success","Delete account successfully",null));
        }else {
            session.setAttribute("DeleteAccountFailed", new MessageOject("Failed","Delete account failed",null));
        }
        return "redirect:/admin/userManagement/customers";
    }

    // block employee
    @GetMapping("/employee/block/{id}")
    public String blockEmployee(@PathVariable("id") int id, Model model, HttpSession session) {
        MessageOject messageOject = adminService.blockEmployee(id);
        session.setAttribute("LockAccountMessage", new MessageOject("Success","Block account successfully",null));

        return "redirect:/admin/userManagement/employee/"+id;
    }

    //unblock employee

    @GetMapping("/employee/unblock/{id}")
    public String unblockEmployee(@PathVariable("id") int id, Model model, HttpSession session) {
        MessageOject messageOject = adminService.unBlockEmployee(id);
        session.setAttribute("UnLockAccountMessage", new MessageOject("Success","Unblock account successfully",null));
        return "redirect:/admin/userManagement/employee/"+id;
    }
    // delete customer handle
    @GetMapping("/employee/delete/{id}")

    public String deleteEmployee(@PathVariable("id") int id, Model model,HttpSession session) {
        MessageOject messageOject = adminService.deleteEmployee(id);

        if(messageOject.getName() == "Success") {
            session.setAttribute("DeleteAccountMessage", new MessageOject("Success","Delete account successfully",null));
        }else {
            session.setAttribute("DeleteAccountFailed", new MessageOject("Failed","Delete account failed",null));

        }

         return "redirect:/admin/userManagement/employees";
    }

    @GetMapping("/employee/approve/{id}")
    public String approvePartner(@PathVariable("id") int id, Model model,HttpSession session) {
        MessageOject messageOject = adminService.approvePartner(id);
        session.setAttribute("ApproveMessage", new MessageOject("Success","Delete account successfully",null));
        return "redirect:/admin/userManagement/applies";
    }

    @GetMapping("/employee/reject/{id}")
    public String rejectPartner(@PathVariable("id") int id, Model model) {
        //MessageOject messageOject = adminService.rejectPartner(id);
        return "redirect:/admin/userManagement/applies";
    }

}
