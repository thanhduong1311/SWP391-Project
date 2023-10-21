package com.demo.homemate.controllers.customer;


import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/customer/history")
@RequiredArgsConstructor
public class CustomerPostController {

    private final BookingService bookingService;
    private  final AdminService adminService;

    @GetMapping("")
    public String viewBookingsHitory(Model model) {

        //Test with id = hardcode
        Customer customer = adminService.getACustomer(2);

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

}
