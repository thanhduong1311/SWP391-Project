package com.demo.homemate.controllers.customer;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.dtos.payment.PaymentRequest;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Ranking;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.EmailService;
import com.demo.homemate.services.RankingService;
import com.demo.homemate.services.interfaces.*;
import com.demo.mservice.enums.RequestType;
import com.demo.mservice.momo.MomoPay;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    private final RankingService rankingService;

    @GetMapping("/form")
    public String createBooking(Model model,HttpSession session,
                                @CookieValue(name = "Token",required = false) String cookieToken,
                                @SessionAttribute(value="SessionToken",required = false) String sessionToken
    ) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;
        JWTService jwt = new JWTService();

        String username = (String) JWTService.parseJwt(token).get("Username");
        Customer customer =  customerRepository.findByUsername(username);

        String message =(String)(session.getAttribute("CustomerMessage"));
        session.removeAttribute("CustomerMessage");
        model.addAttribute("CustomerMessage", message);

        if (token!=null){
            try {
                Claims claim = jwt.parseJwt(token);
                if(claim.getSubject().equals(Role.CUSTOMER.toString())){
                    JobRequest jr = new JobRequest();
                    jr.setCustomerName(customer.getFullName());
                    jr.setCustomerPhone(customer.getPhone());
                    jr.setCustomerAddress(customer.getAddress_detail()+", " +
                    customer.getDistrict() + ", " + customer.getCity());
                    Ranking ranking = rankingService.getRank(username);
                    if (ranking!=null){
                        model.addAttribute("ranking",ranking);
                    }
                    model.addAttribute("bookingInfor",jr);
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
    public String booking(JobRequest request, Model model, @CookieValue(name = "Token",required = false) String cookieToken,
                          @SessionAttribute(value="SessionToken",required = false) String sessionToken, HttpSession session) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");
        request.setCustomerID(customerRepository.findByUsername(username).getCustomerId());

        Ranking ranking = rankingService.getRank(username);
        int rankID=0;
        if (ranking!=null){
           rankID = ranking.getRankId();
        }
        double rawPrice = paymentService.getTotalMoney(request.getTimeService(), request.getServiceId());
        double discountedPrice = paymentService.getDiscount(rawPrice,rankID,request.getServiceId());
        long amount = paymentService.convertMoney(discountedPrice);
        String serviceName = serviceService.getServiceDetail(request.getServiceId()).getName();

        request.setAmount(amount);
        request.setTimeService(request.getTimeService());
        request.setServiceName(serviceName);

        PaymentRequest pr = new PaymentRequest();
        pr.setAmount(amount);
        pr.setCustomerID(request.getCustomerID());


        System.out.println("+y1362178346876348732+      "  +request.getJobAddress());

        if(request.getPaymentType() == 0) {
                model.addAttribute("JobRequest", request);
                model.addAttribute("PaymentRequest",pr);
                MessageOject messageOject = bookingService.createJobWithoutPayment(request);

                if( messageOject.getName().equals("Success")) {
                    return checkOut(model);

                } else {
                    session.setAttribute("CustomerMessage",messageOject.getName()+"#"+messageOject.getMessage());
                    return  "redirect:/customer/bookings/form";
                }

        } else {
          MessageOject messageOject = bookingService.createJob(request);


            if( messageOject.getName().equals("Success")) {
                session.setAttribute("CustomerMessage",messageOject.getName()+"#"+messageOject.getMessage());
                emailService.sendEmail(messageOject.getEmailMessage());
                System.out.println(messageOject.getMessage());
                return "redirect:/customer";
            } else {
                session.setAttribute("CustomerMessage",messageOject.getName()+"#"+messageOject.getMessage());
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
    public String payment(HttpServletRequest request, PaymentRequest paymentRequest,HttpSession session) {

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
            session.setAttribute("CustomerMessage","Error#There are some error when checkout!");
            return "redirect:/customer/bookings/form";
        } else {
            return "redirect:" + payLink;
        }
    }

    @GetMapping("/completeBooking/{id}")
    public String completeWithPayment(HttpServletRequest request,Model model,@PathVariable("id") int id,HttpSession session) {

        Customer customer = adminService.getACustomer(id);

        if (customer == null) {
            session.setAttribute("CustomerMessage","Failed#There some error occur");
            return "redirect:/customer/bookings/form";
        } else {
            MessageOject messageOject = bookingService.completeCreateJob(id);
            emailService.sendEmail(messageOject.getEmailMessage());
            session.setAttribute("CustomerMessage",messageOject.getName()+"#"+messageOject.getMessage());
            return "redirect:/customer";
        }



    }

}
