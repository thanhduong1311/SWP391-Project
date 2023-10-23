package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.*;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.PaymentType;
import com.demo.homemate.repositories.*;
import com.demo.homemate.services.interfaces.IEmployeeService;
import com.demo.homemate.utils.JobTimer;
import com.demo.homemate.utils.PasswordMD5;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final JobRepository jobRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final ServiceRepository serviceRepository;

    private final PaymentService paymentService;

    private final UserService userService;

    private final IncomeRepository incomeRepository;

    @SneakyThrows
    @Override
    public List<JobDetail> getAvailableJob() {
        try {
            List<JobDetail> availableJob = new ArrayList<>();

            List<Job> jobs = jobRepository.findByStatus(JobStatus.AVAILABLE);

            JobTimer jobTimer = new JobTimer();

            for (Job j : jobs) {

                if(!jobTimer.isExpired(j.getStart())) {

                JobDetail jobOverView = new JobDetail();
                Customer customer = new Customer();

                jobOverView.setCustomerName(j.getCustomerId().getFullName());
                jobOverView.setServiceName(j.getServiceId().getName());
                jobOverView.setPrice(paymentService.getTotalMoney(paymentService.getTotalTime(j.getStart(),j.getEnd()),j.getServiceId().getServiceId()));
                jobOverView.setStatus(j.getStatus());
                jobOverView.setJobID(j.getJobId());
                jobOverView.setCustomerAvt(j.getCustomerId().getAvatar());
                jobOverView.setPaymentType(j.getPaymentType());

                availableJob.add(jobOverView);
            }}


            return availableJob;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @SneakyThrows
    @Override
    public JobDetail viewDetailJob(int jobID) {
        try {

            Job job = jobRepository.findById(jobID);
            Customer customer = customerRepository.findById(job.getCustomerId().getCustomerId());
            Service service = serviceRepository.findById(job.getServiceId().getServiceId());


            JobDetail jobDetail = new JobDetail();

            jobDetail.setCustomerID(customer.getCustomerId());
            jobDetail.setJobID(jobID);

            jobDetail.setCustomerName(customer.getFullName());
            jobDetail.setServiceName(service.getName());
            jobDetail.setAddress(customer.getAddress_detail() + ", " + customer.getDistrict() + ", " + customer.getCity());
            jobDetail.setStart(job.getStart());
            jobDetail.setEnd(job.getEnd());
            jobDetail.setPhone(customer.getPhone());
            jobDetail.setJobDescription(job.getDescription());

            jobDetail.setPaymentType(job.getPaymentType());
            jobDetail.setStatus(job.getStatus());
            jobDetail.setCustomerAvt(customer.getAvatar());

            jobDetail.setServiceTime(paymentService.getTotalTime(job.getStart(),job.getEnd()));
            jobDetail.setPrice(paymentService.getTotalMoney(paymentService.getTotalTime(job.getStart(),job.getEnd()),job.getServiceId().getServiceId()));

            return jobDetail;
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }
    }

    @Override
    public MessageOject changePassword(ChangePasswordRequest request) {
        try {
            String username = request.getUsername();
            String oldPas = request.getOldPassword();
            String newPasString= request.getNewPassword();
            String confirmPass = request.getConfirmPassword();

            int check = userService.checkNewChangePassword(username,oldPas,newPasString,confirmPass);

            if(check == 0 ) {
                return new MessageOject("Failed","Username is not exist",null);
            }
            if(check == 1 ) {
                return new MessageOject("Failed","Not allow password null here",null);
            }
            if(check == 2) {
                return new MessageOject("Failed","Old password is incorrect",null);
            }
            if(check == 3) {
                return new MessageOject("Failed","New password at least 6 character and include uppercase, lowercase and special characters",null);
            }
            if(check == 4) {
                return new MessageOject("Failed","New password is not match with confirm password",null);
            }
            if(check == 5) {
                Employee employee = employeeRepository.findByUsername(username);
                employee.setPassword(PasswordMD5.encode(newPasString));
                employeeRepository.save(employee);
                return new MessageOject("Success","Change password successfully",null);
            }
            return new MessageOject("Failed","Something went wrong when chaning password",null);
        } catch (Exception e) {
            return new MessageOject("Error",e.getMessage(),null);
        }
    }

    @Override
    public List<JobDetail> getAvailableJobForSpecialEmployee(int employeeID) {
        return null;
    }

    @Override
    public MessageOject takeJob(int jobID,int employeeID) {
        try {

            //GET job and employee, get employee job;
            Job job = jobRepository.findById(jobID);
            Employee employee = employeeRepository.findById(employeeID);
            List<Job> myJob  = jobRepository.findByEmployeeId(employee);

            boolean isBusy = false;
            JobTimer jobTimer = new JobTimer();

            for (Job j: myJob) {
                // check is employee busy
                if(jobTimer.checkBusy(j.getStart(),j.getEnd(),job.getStart()) ||
                        jobTimer.checkBusy(j.getStart(),j.getEnd(),job.getEnd())) {
                    isBusy = true;
                    break;
                }
            }

            // refuse if employee is busy
            if(isBusy) {
                return new MessageOject("Failed", "You early have job at this time,please check your schedule!", null);
            }else {
                // check balance;
                double time = paymentService.getTotalTime(job.getStart(),job.getEnd());
                double empBalace = employee.getBalance();
                // Here 0.02 is 2% of commission for each job
                double commission = paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) * 0.02;


                //for CASH
                if(job.getPaymentType() == PaymentType.CASH) {
                    if(empBalace - commission >= 0) {
                        job.setStatus(JobStatus.INPROGRESS);
                        job.setEmployeeId(employee);
                        employee.setBalance(empBalace-commission);
                        ///

                        employeeRepository.save(employee);
                        jobRepository.save(job);

                        return new MessageOject("Success", "Receive job is successfully!", null);


                    } else {
                        return new MessageOject("Failed", "Your balance is not enough to take this job! Top up your account and try again!", null);
                    }

                } else { // for BANKING
                    job.setStatus(JobStatus.INPROGRESS);
                    job.setEmployeeId(employee);
                    employee.setBalance(empBalace+(paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) - paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) * 0.02));
                    ///

                    employeeRepository.save(employee);
                    jobRepository.save(job);

                    return new MessageOject("Success", "Receive job is successfully!", null);
                }
            }

        } catch (Exception e) {
            return new MessageOject("Error", "There some error when taking job.",null);
        }
    }

    @Override
    public MessageOject cancelJob(int JobID, int employeeID) {
//        try {
//            EmployeeRequest request = new EmployeeRequest();
//            Job job = jobRepository.findById(JobID);
//            Employee employee =  employeeRepository.findById(employeeID);
//
//
//
//
//            return new MessageOject("Success","Request created, you must watting for the approve.",null);
//        } catch (Exception e) {
//            return new MessageOject("Error","There some error occur, can not make cancel requets",null);
//        }
        return  null;
    }

    @Override
    public MessageOject doneJob(int JobID, int employeeID) {
        try {
            Job job = jobRepository.findById(JobID);
            Employee employee = employeeRepository.findById(employeeID);

            if(employee != null) {
                JobTimer jobTimer = new JobTimer();
                if(jobTimer.checkDone(job.getEnd(),new Date())) {
                    job.setStatus(JobStatus.DONE);
                    Income income = new Income();
                    income.setEmployeeId(employee);
                    income.setJobId(job);
                    double time = paymentService.getTotalTime(job.getStart(),job.getEnd());
                    income.setAmount(paymentService.getTotalMoney(time, job.getServiceId().getServiceId()));
                    income.setCreateAt(new Date());
                    income.setUpdateAt(new Date());

                    jobRepository.save(job);
                    incomeRepository.save(income);

                    return new MessageOject("Success","Job complete, you can check at your incomes",null);

                } else {
                    return new MessageOject("Failed","You cannot complete the work at this time",null);
                }
            }
            return new MessageOject("Error", "There some error occur, try again",null);

        } catch (Exception e) {
            return new MessageOject("Error", "There some error occur, try again",null);
        }
    }

    @Override
    public List<Income> getIncomes(int employeeID) {
        try {
            Employee employee = employeeRepository.findById(employeeID);
            List<Income> incomes = incomeRepository.findByEmployeeId(employee);
            return  incomes;
        } catch (Exception e) {
             return  null;
        }
    }


}
