package com.demo.homemate.controllers.customer;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.customerReport.responese.CustomerReportJob;
import com.demo.homemate.dtos.feedback.FeedbackRequest;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.BookingService;
import com.demo.homemate.services.CustomerReportService;
import com.demo.homemate.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@Controller
@RequestMapping("/customer/history")
@RequiredArgsConstructor
public class CustomerPostController {

    private final BookingService bookingService;
    private  final AdminService adminService;
    private  final CustomerService customerService;

    private final CustomerRepository customerRepository;

    private final CustomerReportService customerReportService;


    @GetMapping("")
    public String viewBookingsHitory(Model model,
                                     @CookieValue(name = "Token",required = false) String cookieToken,
                                     @SessionAttribute(value="SessionToken",required = false) String sessionToken) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;
       String username = (String)JWTService.parseJwt(token).get("Username");
        //Test with id = hardcode
        Customer customer = adminService.getACustomer(customerRepository.findByUsername(username).getCustomerId());
        List<JobDetail> bookings = bookingService.getCustomerBookings(customer.getCustomerId());
        model.addAttribute("bookings",bookings);
        return "customer/bookingHistory";
    }
    @GetMapping("detailBooking/{id}")
    public String detailBooking(Model model, @PathVariable("id") int jobId) {
        JobDetail jobDetail = bookingService.getAJob(jobId);
        model.addAttribute("jobDetail", jobDetail);
        return "customer/bookingDetail";
    }
    @GetMapping("feedback/{id}")
    public String viewFeedback(Model model, @PathVariable("id") int jobId) {
        try{
            FeedbackRequest feedRequest = new FeedbackRequest();
            JobDetail jobDetail = bookingService.getAJob(jobId);
            if (jobDetail.getStatus() != JobStatus.DONE){
                MessageOject mo = new MessageOject("Failed","This job hasn't done yet",null);
                model.addAttribute("MessageResult",mo);
                return "redirect:/customer/history";
            }
            if (customerService.getFeeback(jobId)!=null){
                feedRequest =customerService.getFeeback(jobId);
            }else{
                feedRequest.setJobId(jobId).setFeedbackId(jobId).setCustomerId(jobDetail.getCustomerID());
            }
            model.addAttribute("feedbackInfo", feedRequest);
            model.addAttribute("JobInfo",jobDetail);
        }catch (Exception e){
        }
        return "customer/customer-review";
    }
    @PostMapping("feedback")
    public String feedback(Model model,
                                     @CookieValue(name = "Token",required = false) String cookieToken,
                                     @SessionAttribute(value="SessionToken",required = false) String sessionToken,
                                     FeedbackRequest feedbackInfo
                                     ) {
        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token = cookieToken!=null?cookieToken:sessionToken;
        MessageOject mo = customerService.feedback(feedbackInfo,feedbackInfo.getCustomerId());
        model.addAttribute("MessageFeedback",mo);
        return "redirect:/customer/history";
    }
    @GetMapping("report/{id}")
    public String viewReport(Model model, @PathVariable("id") int jobId) {
        try{
            CustomerReportJob customerReportJob;
            JobDetail jobDetail = bookingService.getAJob(jobId);
            if (jobDetail.getStatus() == JobStatus.AVAILABLE){
                MessageOject mo = new MessageOject("Failed","This job hasn't start yet",null);
                model.addAttribute("MessageResult",mo);
                return "redirect:/customer/history";
            }
            if (customerReportService.getReportByJobID(jobId)!=null){
                customerReportJob =customerReportService.getReportByJobID(jobId);
            }else{
                return "redirect:/customer/history";
            }
            model.addAttribute("reportInfo", customerReportJob);
            model.addAttribute("Job",jobDetail);
        }catch (Exception e){
        }
        return "customer/customer-report";
    }
    @PostMapping("report")
    public String report(Model model,
                           @CookieValue(name = "Token",required = false) String cookieToken,
                           @SessionAttribute(value="SessionToken",required = false) String sessionToken,
                           CustomerReportJob customerReportJob
    ) {
        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }

        String token = cookieToken!=null?cookieToken:sessionToken;
        MessageOject mo = customerReportService.report(customerReportJob);
        model.addAttribute("MessageFeedback",mo);
        return "redirect:/customer/history";
    }
    @GetMapping("deleteReport/{id}")
    public String deleteReport(Model model,
                               @PathVariable("id") int jobId){
        MessageOject messageOject = customerReportService.deleteReport(jobId);
        model.addAttribute("DeleteReportMessage",messageOject);
        System.out.println(messageOject.getMessage());
        return "redirect:/customer/history";

    }

}
