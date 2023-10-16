package com.demo.homemate.controllers.admin;

import com.demo.homemate.dtos.customerReport.responese.CustomerReportJob;
import com.demo.homemate.dtos.employeeRequest.Response.CancelJobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.CustomerReportService;
import com.demo.homemate.services.EmployeeRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final AdminService adminService;
    private final EmployeeRequestService employeeRequestService;
    private final CustomerReportService customerReportService;

    @GetMapping(value = "")
    public String viewTaskManagement(Model model) {
        model.addAttribute("requestList", employeeRequestService.getRequestList());
        model.addAttribute("reportList", customerReportService.getReportList());

        return "admin/taskManagement";
    }

    @GetMapping("/requests")
    public String viewRequest(Model model) {
        model.addAttribute("requestList", employeeRequestService.getRequestList());
        return "admin/employeeRequest";
    }

    @GetMapping("/reports")
    public String viewReport(Model model) {
        model.addAttribute("reportList", customerReportService.getReportList());
        return "admin/customerReport";
    }

    @GetMapping("/request/{id}")
    public String detailTask(@PathVariable("id") int id, Model model) {
        CancelJobDetail cancelJobDetail = employeeRequestService.getCancelJobDetailByRequest(id);
       model.addAttribute("cancelRequestDetail",cancelJobDetail);
        return "admin/RequestDetail";
    }

    @GetMapping("reject/{id}")
    public String rejectRequest(@PathVariable("id") int id) {
        MessageOject ms = employeeRequestService.rejectRequest(id);
        return "redirect:/admin/tasks/request/" + id;
    }

    @GetMapping("approve/{id}")
    public String apporveRequest(@PathVariable("id") int id) {
        MessageOject ms = employeeRequestService.apporveRequest(id);
        return "redirect:/admin/tasks/request/" + id;
    }


    @GetMapping("delete/{id}")
    public String deleteRequest(@PathVariable("id") int id) {
        MessageOject ms = employeeRequestService.deleteRequest(id);
        return "redirect:/admin/tasks/requests";
    }

    @GetMapping("report/{id}")
    public String viewReportDerail(@PathVariable("id") int id,Model model) {
        CustomerReportJob customerReportJob = customerReportService.getReportDetailByID(id);
        model.addAttribute("reportDetail",customerReportJob);
        return "/admin/reportDetail";
    }

    @GetMapping("deleteReport/{id}")
    public String deleteReport(@PathVariable("id") int id) {
        MessageOject ms = customerReportService.deleteReport(id);
        return "redirect:/admin/tasks/reports";
    }
}
