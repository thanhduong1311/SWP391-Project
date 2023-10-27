package com.demo.homemate.controllers.customer;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.dtos.payment.PaymentRequest;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.EmailService;
import com.demo.homemate.services.interfaces.*;
import com.demo.mservice.enums.RequestType;
import com.demo.mservice.momo.MomoPay;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer/bookings")
@RequiredArgsConstructor
public class CustomerBookingController {

    private final IServiceService serviceService;

    private final IPaymentService paymentService;

    private final IJobService bookingService;

    private final EmailService emailService;

    private final AdminService adminService;

    private final CustomerRepository customerRepository;

    @GetMapping("/form")
    public String createBooking(Model model,
                                @CookieValue(name = "Token",required = false) String cookieToken,
                                @SessionAttribute(value="SessionToken",required = false) String sessionToken
    ) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;
        JWTService jwt = new JWTService();

        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                if(claim.getSubject().equals(Role.CUSTOMER.toString())){
                    model.addAttribute("bookingInfor",new JobRequest());
                    model.addAttribute("service", serviceService.getAllServices());
                    return "customer/booking";
                }
                else return "redirect:/home";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else return "redirect:/home";


    }

    @PostMapping("boooking")
    public String booking(JobRequest request, Model model,@CookieValue(name = "Token",required = false) String cookieToken,
                          @SessionAttribute(value="SessionToken",required = false) String sessionToken) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");
        request.setCustomerID(customerRepository.findByUsername(username).getCustomerId());

        double timeService = paymentService.getTotalTime(request.getTimeStart(),request.getTimeEnd());
        double rawPrice = paymentService.getTotalMoney(timeService, request.getServiceId());
        long amount = paymentService.convertMoney(rawPrice);
        String serviceName = serviceService.getServiceDetail(request.getServiceId()).getName();

        request.setAmount(amount);
        request.setTimeService(timeService);
        request.setServiceName(serviceName);

        PaymentRequest pr = new PaymentRequest();
        pr.setAmount(amount);
        pr.setCustomerID(request.getCustomerID());


        if(request.getPaymentType() == 0) {
                model.addAttribute("JobRequest", request);
                model.addAttribute("PaymentRequest",pr);

                MessageOject messageOject = bookingService.createJobWithoutPayment(request);
                System.out.println(messageOject.getMessage());

                if( messageOject.getName().equals("Success")) {
                    return checkOut(model);

                } else {
                    return  "redirect:/customer/bookings/form";
                }

        } else {
          MessageOject messageOject = bookingService.createJob(request);
            emailService.sendEmail(messageOject.getEmailMessage());
            System.out.println(messageOject.getMessage());

            if( messageOject.getName().equals("Success")) {
                return "redirect:/customer";
            } else {
                return  "redirect:/customer/bookings/form";
            }
        }
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model) {
        model.getAttribute("JobRequest");
        model.getAttribute("PaymentRequest");
        return "customer/checkOut";
    }

    @PostMapping("/checkOutWithPayment")
    public String payment(HttpServletRequest request, PaymentRequest paymentRequest) {

        int cus = paymentRequest.getCustomerID();
        long amo = paymentRequest.getAmount();

        RequestType requestType;
        if ("captureWallet".equals(paymentRequest.getPaymentType())) {
            requestType = RequestType.CAPTURE_WALLET;
        } else {
            requestType = RequestType.PAY_WITH_ATM;
        }

        String payLink = MomoPay.getPayLink(request, requestType, paymentRequest.getAmount(),paymentRequest.getCustomerID());

        if (payLink == null) {
            request.getSession().setAttribute("Error", "There are some error when checkout!");
            return "redirect:/customer/bookings/form";
        } else {
            return "redirect:" + payLink;
        }
    }

    @GetMapping("/completeBooking/{id}")
    public String completeWithPayment(HttpServletRequest request,Model model,@PathVariable("id") int id) {

        Customer customer = adminService.getACustomer(id);

        if (customer == null) {
            MessageOject messageOject = new MessageOject("Failed", "There some error occur", null);
            System.out.println(messageOject.getMessage());
            return "redirect:/customer/bookings/form";
        } else {

            MessageOject messageOject = bookingService.completeCreateJob(id);

            emailService.sendEmail(messageOject.getEmailMessage());

            System.out.println(messageOject.getMessage());

            return "redirect:/customer";
        }



    }

}
