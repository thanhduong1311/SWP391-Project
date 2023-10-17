package com.demo.homemate.controllers.customer;

import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.services.interfaces.IServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @PostMapping("boooking")
    public String booking(JobRequest request) {
        System.out.println(request.toString());

        String times=request.getTimeStart().split("T")[1];
        String timee=request.getTimeEnd().split("T")[1];







        int hs = Integer.parseInt(times.split(":")[0]);
        int ms = Integer.parseInt(times.split(":")[1]);

        int he = Integer.parseInt(timee.split(":")[0]);
        int me = Integer.parseInt(timee.split(":")[1]);


//        Date Start = new Date(ys,mos,ds,hs,ms);
//        Date End = new Date(ye,moe,de,he,me);

        System.out.println("=====================================================");
//        System.out.println("start at "+ Start);
//        System.out.println("end at " + End);
        System.out.println("=====================================================");


        return "redirect:/customer/bookings/form";
    }

}
