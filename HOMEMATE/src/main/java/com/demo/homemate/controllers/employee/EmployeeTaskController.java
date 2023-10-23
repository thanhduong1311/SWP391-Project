package com.demo.homemate.controllers.employee;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Income;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee/job")
@RequiredArgsConstructor
public class EmployeeTaskController {

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;
    @GetMapping("/jobList")
    public String viewJObs(Model model) {
        List<JobDetail> jobs = employeeService.getAvailableJob();
        model.addAttribute("jobs",jobs);
        return "employee/jobList";
    }

    @GetMapping("/{id}")
    public String viewJobDetail(Model model, @PathVariable("id") int id) {

        JobDetail  job = employeeService.viewDetailJob(id);

        model.addAttribute("job",job);

        return "employee/jobDetail";
    }

    @GetMapping("/incomes")
    public String viewIncomes(Model model ) {
        List<Income> incomes = employeeService.getIncomes(12);

        for (Income i:
             incomes) {
            System.out.println(i.toString());
        }
        return "redirect:/employee";
    }

    @GetMapping("/imcome/{id}")
    public String viewDetailIncome(@PathVariable("id") int id) {

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
    public String cancelJob(@PathVariable("id") int id) {
        return "";
    }


}
