package com.demo.homemate.controllers.employee;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.employeeCancelRequest.Request.CreateCancelRequest;
import com.demo.homemate.dtos.job.response.CalendarObject;
import com.demo.homemate.dtos.job.response.IncomeDetail;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Income;
import com.demo.homemate.enums.AccountStatus;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee/job")
@RequiredArgsConstructor
public class EmployeeTaskController {

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;
    @GetMapping("/jobList")
    public String viewJObs(Model model ,@CookieValue(name = "Token",required = false) String cookieToken,
                           @SessionAttribute(value="SessionToken",required = false) String sessionToken) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");

        Employee auth = employeeRepository.findByUsername(username);

        List<JobDetail> jobs = new ArrayList<>();

    if(auth.getAccountStatus() == AccountStatus.WAIT_FOR_APPROVE) {
        model.addAttribute("jobs", jobs );

    } else {
        jobs = employeeService.getAvailableJob();
        model.addAttribute("jobs",jobs);
    }
        return "employee/jobList";
    }

    @GetMapping("/{id}")
    public String viewJobDetail(Model model, @PathVariable("id") int id) {
        JobDetail  job = employeeService.viewDetailJob(id);
        model.addAttribute("job",job);
        return "employee/jobDetail";
    }

    @GetMapping("/incomes")
    public String viewIncomes(Model model , @CookieValue(name = "Token",required = false) String cookieToken,
                              @SessionAttribute(value="SessionToken",required = false) String sessionToken) {
        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");

        int empID = employeeRepository.findByUsername(username).getEmployeeId();
//
        List<IncomeDetail> incomes = employeeService.getIncomes(empID);

        model.addAttribute("incomes", incomes);


        return "employee/incomes";
    }

    @GetMapping("/income/{id}")
    public String viewDetailIncome(@PathVariable("id") int id, Model model) {

        IncomeDetail incomeDetail = employeeService.getDetailIncome(id);

        model.addAttribute("income",incomeDetail);

        return "employee/incomeDetail";
    }

    @GetMapping("/take/{id}")
    public  String takeJob(@PathVariable("id") int id, @CookieValue(name = "Token",required = false) String cookieToken,
                           @SessionAttribute(value="SessionToken",required = false) String sessionToken) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");

        int empID = employeeRepository.findByUsername(username).getEmployeeId();

        MessageOject messageOject = employeeService.takeJob(id,empID);

        System.out.println(messageOject.getMessage());

        return "redirect:/employee/job/" + id;
    }

    @GetMapping("/done/{id}")
    public  String doneJob(@PathVariable("id") int id, @CookieValue(name = "Token",required = false) String cookieToken,
                           @SessionAttribute(value="SessionToken",required = false) String sessionToken) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");

        int empID = employeeRepository.findByUsername(username).getEmployeeId();

        MessageOject messageOject = employeeService.doneJob(id,empID);

        System.out.println(messageOject.getMessage());

        return "redirect:/employee/job/" + id;
    }

    @GetMapping("/cancel/{id}")
    public String cancelJobView(@PathVariable("id") int id,Model model) {

        JobDetail  job = employeeService.viewDetailJob(id);

        model.addAttribute("job",job);
        model.addAttribute("reason",new CreateCancelRequest());
        return "employee/cancelJob";
    }

    @PostMapping("/cancelJob/{id}")
    public String cancelJob(@PathVariable("id") int id,CreateCancelRequest reason) {


        MessageOject messageOject = employeeService.cancelJob(id, reason.getReason());
        System.out.println(messageOject.getMessage());
        return "redirect:/employee/job/jobList";
    }


    @GetMapping("/schedule")
    public String viewSchedule(Model model, @CookieValue(name = "Token",required = false) String cookieToken,
                               @SessionAttribute(value="SessionToken",required = false) String sessionToken) {

        if (cookieToken == null && sessionToken==null) {
            return "redirect:/login";
        }
        String token=cookieToken!=null?cookieToken:sessionToken;

        String username = (String) JWTService.parseJwt(token).get("Username");

        int empID = employeeRepository.findByUsername(username).getEmployeeId();
        CalendarObject calendarObject = new CalendarObject();

        String s = employeeService.getScheduleJSON(empID);


        model.addAttribute("schedule", s);

        return "employee/schedule";
    }

}
