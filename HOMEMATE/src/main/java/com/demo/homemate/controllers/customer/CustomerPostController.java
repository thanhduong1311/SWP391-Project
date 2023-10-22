package com.demo.homemate.controllers.customer;


import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.BookingService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/customer/history")
@RequiredArgsConstructor
public class CustomerPostController {

    private final BookingService bookingService;
    private  final AdminService adminService;
    private final CustomerRepository customerRepository;

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

}
