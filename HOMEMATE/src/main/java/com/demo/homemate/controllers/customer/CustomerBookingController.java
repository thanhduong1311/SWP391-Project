package com.demo.homemate.controllers.customer;

import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.services.interfaces.IServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer/bookings")
@RequiredArgsConstructor
public class CustomerBookingController {

    private final IServiceService serviceService;

    @GetMapping("/form")
    public String createBooking(Model model) {

        model.addAttribute("bookingInfor",new JobRequest());

        model.addAttribute("service", serviceService.getAllServices());
        return "customer/booking";
    }

}
