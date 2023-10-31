package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.dtos.job.response.CalendarObject;
import com.demo.homemate.dtos.job.response.IncomeDetail;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.*;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.PaymentType;
import com.demo.homemate.enums.RequestStatus;
import com.demo.homemate.repositories.*;
import com.demo.homemate.services.interfaces.IEmployeeService;
import com.demo.homemate.utils.JobTimer;
import com.demo.homemate.utils.PasswordMD5;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
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

    private final EmployeeRequestRepository employeeRequestRepository;

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
                    jobOverView.setJobInCalendar(JobTimer.convertDateToString(j.getStart()));

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

            jobDetail.setJobInCalendar(JobTimer.convertDateToString(job.getStart()));

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
    public MessageOject takeJob(int jobID, int employeeID) {
        try {

            //GET job and employee, get employee job;
            Job job = jobRepository.findById(jobID);
            Employee employee = employeeRepository.findById(employeeID);
            List<Job> myJob  = jobRepository.findByEmployeeId(employee);

            boolean isBusy = false;
            JobTimer jobTimer = new JobTimer();

            for (Job j: myJob) {
                // check is employee busy
                if(j.getStatus() == JobStatus.INPROGRESS) {
                    if(jobTimer.checkBusy(j.getStart(),j.getEnd(),job.getStart()) ||
                            jobTimer.checkBusy(j.getStart(),j.getEnd(),job.getEnd())) {
                        isBusy = true;
                        break;
                    }
                }
            }

            // refuse if employee is busy
            if(isBusy) {
                return new MessageOject("Failed", "You early have job at this time,please check your schedule!", null);
            }else {
                if(employee.getAccountStatus().ordinal() != 1) {
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

                            double outMoney = empBalace-commission;
                            ///
                            BigDecimal bd = new BigDecimal(outMoney);
                            bd = bd.setScale(4, RoundingMode.HALF_UP);
                            outMoney = bd.doubleValue();

                            employee.setBalance(outMoney);


                            employeeRepository.save(employee);
                            jobRepository.save(job);

                            return new MessageOject("Success", "Receive job is successfully!", null);


                        } else {
                            return new MessageOject("Failed", "Your balance is not enough to take this job! Top up your account and try again!", null);
                        }

                    } else { // for BANKING
                        job.setStatus(JobStatus.INPROGRESS);
                        job.setEmployeeId(employee);
                        //employee.setBalance(empBalace+(paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) - paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) * 0.02));
                        ///

                        employeeRepository.save(employee);
                        jobRepository.save(job);

                        return new MessageOject("Success", "Receive job is successfully!", null);
                    }
                }else {
                    return new MessageOject("Failed", "Your account is blocked, can not take job!",null);
                }
            }
        } catch (Exception e) {
            return new MessageOject("Error", "There some error when taking job.",null);
        }
    }

    @Override
    public MessageOject cancelJob(int JobID, String reason) {
        try {
            EmployeeRequest request = new EmployeeRequest();
            Job job = jobRepository.findById(JobID);
            Employee employee =  employeeRepository.findById(job.getEmployeeId().getEmployeeId());
            JobTimer timer = new JobTimer();

            boolean checkValid = timer.checkValidCancel(new Date(),job.getStart());
            if(!checkValid) {
                return new MessageOject("Failed","You can not cancel this job because time is nearly job start time.",null);
            } else  {

                EmployeeRequest er = employeeRequestRepository.findByJobId(job);

                if (er != null) {
                    return new MessageOject("Failed","You are early request a cancel in this job, please wait for approve.",null);
                } else {
                    request.setStatus(RequestStatus.WAITING);
                    request.setJobId(job);
                    request.setEmployeeId(employee);
                    request.setReason(reason);
                    request.setCreateAt(new Date());
                    request.setUpdateAt(new Date());

                    employeeRequestRepository.save(request);

                    return new MessageOject("Success","Cancel request is created, please wait the response.",null);

                }

                 }
        } catch (Exception e) {
            return new MessageOject("Error","There some error occur, can not make cancel request",null);
        }
    }

    @Override
    public MessageOject doneJob(int JobID, int employeeID) {
        try {
            Job job = jobRepository.findById(JobID);
            Employee employee = employeeRepository.findById(employeeID);
            Customer customer = customerRepository.findById(job.getCustomerId().getCustomerId());

            System.out.println(customer.getUsername() + "ádasjndakjndkjasndkjlasndjas");
            if(employee != null) {
                JobTimer jobTimer = new JobTimer();
                if(jobTimer.checkDone(job.getEnd(),new Date())) {
                    job.setStatus(JobStatus.DONE);
                    Income income = new Income();
                    income.setEmployeeId(employee);
                    income.setJobId(job);
                    income.setCreateAt(new Date());
                    income.setUpdateAt(new Date());

                    double time = paymentService.getTotalTime(job.getStart(),job.getEnd());
                   // income.setAmount(paymentService.getTotalMoney(time, job.getServiceId().getServiceId()));


                    double empBalace = employee.getBalance();
                    // Here 0.02 is 2% of commission for each job
                    double commission = paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) * 0.02;


                    if(job.getPaymentType() == PaymentType.CASH) {

                            double spend = paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) + customer.getTotalSpend();

                           BigDecimal bd = new BigDecimal(spend);
                            bd = bd.setScale(4, RoundingMode.HALF_UP);
                              spend = bd.doubleValue();

                            customer.setTotalSpend(spend);
                            customerRepository.save(customer);


                            income.setAmount(commission*-1);
                            employeeRepository.save(employee);
                            incomeRepository.save(income);
                            return new MessageOject("Success", "Job completed, you can check in your income!", null);

                    } else { // for BANKING
                        double inMoney = empBalace+(paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) - paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) * 0.02);
                        ///
                        BigDecimal bd = new BigDecimal(inMoney);
                        bd = bd.setScale(4, RoundingMode.HALF_UP);
                        inMoney = bd.doubleValue();


                        employee.setBalance(inMoney);
                        employeeRepository.save(employee);

                        double afterDis = (paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) - paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) * 0.02);

                        BigDecimal bd2 = new BigDecimal(afterDis);
                        bd2 = bd2.setScale(4, RoundingMode.HALF_UP);
                        afterDis = bd2.doubleValue();
                        income.setAmount(afterDis);

                        incomeRepository.save(income);

                        double spend = paymentService.getTotalMoney(time,job.getServiceId().getServiceId()) + customer.getTotalSpend();

                        BigDecimal bd3 = new BigDecimal(spend);
                        bd3 = bd3.setScale(4, RoundingMode.HALF_UP);
                        spend = bd3.doubleValue();

                        customer.setTotalSpend(spend);
                        customerRepository.save(customer);

                        return new MessageOject("Success", "Job completed, you can check in your income!", null);
                    }

                } else {
                    return new MessageOject("Warning","You cannot complete the work at this time",null);
                }
            }
            return new MessageOject("Error", "There some error occur, try again",null);

        } catch (Exception e) {
            return new MessageOject("Error", "There some error occur, try again",null);
        }
    }

    @Override
    public List<IncomeDetail> getIncomes(int employeeID) {
        try {
            List<IncomeDetail> result = new ArrayList<>();
            List<Income> incomes = incomeRepository.findIncomeByEmployeeId(employeeRepository.findById(employeeID));
            if (incomes != null) {
                for (Income i : incomes
                     ) {
                    IncomeDetail incDel = new IncomeDetail();
                    Job job = jobRepository.findById(i.getJobId().getJobId());
                    double totalTime = paymentService.getTotalTime(
                            jobRepository.findById(i.getJobId().getJobId()).getStart(),
                            jobRepository.findById(i.getJobId().getJobId()).getEnd());


                    double amount =paymentService.getTotalMoney(totalTime,jobRepository.findById(i.getJobId().getJobId()).getServiceId().getServiceId());


                    double commission = amount*0.02;

                    BigDecimal bd = new BigDecimal(commission);
                    bd = bd.setScale(2, RoundingMode.HALF_UP);
                    commission = bd.doubleValue();

                    incDel.setStatus(job.getStatus());
                    incDel.setIncomeId(i.getIncomeId());
                    incDel.setCustomerName(customerRepository.findById(i.getJobId().getCustomerId().getCustomerId()).getFullName());
                    incDel.setCustomerPhone(customerRepository.findById(i.getJobId().getCustomerId().getCustomerId()).getPhone());
                    incDel.setAddress(
                            customerRepository.findById(i.getJobId().getCustomerId().getCustomerId()).getAddress_detail()+", "+
                            customerRepository.findById(i.getJobId().getCustomerId().getCustomerId()).getDistrict() + "," +
                            customerRepository.findById(i.getJobId().getCustomerId().getCustomerId()).getCity()
                    );
                    incDel.setRealAmount(i.getAmount());
                    incDel.setAmountFromJob(amount);
                    incDel.setCommission(commission);
                    incDel.setPaymentType(jobRepository.findById(i.getJobId().getJobId()).getPaymentType());
                    incDel.setTotalTime(totalTime);
                    incDel.setForm(jobRepository.findById(i.getJobId().getJobId()).getStart());
                    incDel.setTo(jobRepository.findById(i.getJobId().getJobId()).getEnd());
                    incDel.setDateOfIncome(i.getCreateAt());
                    incDel.setServiceName(jobRepository.findById(i.getJobId().getJobId()).getServiceId().getName());
                    incDel.setNote(i.getNote());
                    incDel.setCustomerAvt(customerRepository.findById(jobRepository.findById(i.getJobId().getJobId()).getCustomerId().getCustomerId()).getAvatar());
                    result.add(incDel);
                }
            }

            return result;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @SneakyThrows
    @Override
    public IncomeDetail getDetailIncome(int incomeId) {
        try {
            Income i = incomeRepository.findIncomeByIncomeId(incomeId);

            IncomeDetail incDel = new IncomeDetail();
            Customer customer =customerRepository.findById(i.getJobId().getCustomerId().getCustomerId());
            Job job = jobRepository.findById(i.getJobId().getJobId());
            double totalTime = paymentService.getTotalTime(
                    job.getStart(),
                    job.getEnd());


            double amount =paymentService.getTotalMoney(totalTime,jobRepository.findById(i.getJobId().getJobId()).getServiceId().getServiceId());


            double commission = amount*0.02;

            BigDecimal bd = new BigDecimal(commission);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            commission = bd.doubleValue();

            incDel.setStatus(job.getStatus());
            incDel.setIncomeId(i.getIncomeId());



            incDel.setIncomeId(i.getIncomeId());
            incDel.setCustomerName(customerRepository.findById(i.getJobId().getCustomerId().getCustomerId()).getFullName());
            incDel.setCustomerPhone(customerRepository.findById(i.getJobId().getCustomerId().getCustomerId()).getPhone());
            incDel.setAddress(
                    customer.getAddress_detail()+", "+
                            customer.getDistrict() + "," +
                            customer.getCity()
            );
            incDel.setRealAmount(i.getAmount());
            incDel.setAmountFromJob(amount);
            incDel.setCommission(commission);
            incDel.setPaymentType(jobRepository.findById(i.getJobId().getJobId()).getPaymentType());
            incDel.setTotalTime(totalTime);
            incDel.setForm(jobRepository.findById(i.getJobId().getJobId()).getStart());
            incDel.setTo(jobRepository.findById(i.getJobId().getJobId()).getEnd());
            incDel.setDateOfIncome(i.getCreateAt());
            incDel.setServiceName(jobRepository.findById(i.getJobId().getJobId()).getServiceId().getName());
            incDel.setNote(i.getNote());

            return  incDel;
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @SneakyThrows
    @Override
    public String getScheduleJSON(int employeeID) {
        try {
            String schedule ="[";

            List<Job> jobs = jobRepository.findByEmployeeId(employeeRepository.findById(employeeID));

            JobTimer jobTimer = new JobTimer();
            if(jobs !=  null) {
                for (Job j :jobs  ) {
                    if(j.getStatus().ordinal() == 1) {
                        CalendarObject c = new CalendarObject();
                        c.setId(String.valueOf(j.getJobId()));
                        c.setName(j.getServiceId().getName());
                        c.setType("birthday");
                        c.setDescription(
                                j.getStart().getHours() + ":" + j.getStart().getMinutes() +" - "+
                                j.getEnd().getHours() + ":" + j.getEnd().getMinutes() + " " + j.getDescription()
                        );
                        c.setDate(jobTimer.convertDateToString(j.getStart()));
                        schedule += c.toString() + ",";
                    } else {
                        CalendarObject c = new CalendarObject();
                        c.setId(String.valueOf(j.getJobId()));
                        c.setName(j.getServiceId().getName());
                        c.setType("holiday");
                        c.setDescription(
                                j.getStart().getHours() + ":" + j.getStart().getMinutes() +" - "+
                                        j.getEnd().getHours() + ":" + j.getEnd().getMinutes() + " " + j.getDescription()
                        );
                        c.setDate(jobTimer.convertDateToString(j.getStart()));
                        schedule += c.toString() + ",";
                    }
                }
                return schedule.substring(0,schedule.length()-1) + "]";
            } else {
                return schedule +"]";
            }

        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }

    }

    @SneakyThrows
    @Override
    public MessageOject updateProfile(EmployeeProlife request) {
        try {
                Employee employee = employeeRepository.findByUsername(request.getUsername());
                if (employee != null) {

                    // set date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    employee.setDob(sdf.parse(request.getDob()));

                    employee.setFullName(request.getName());

                    if(employee.getAvatar() != request.getAvatar()) {
                        employee.setAvatar(request.getAvatar());

                    }
                    employee.setPhone(request.getPhone());
                    employee.setAddress_detail(request.getAddress());
                    employee.setCity(request.getCity());
                    employee.setDistrict(request.getDistrict());
                    employee.setIdCardNumber(request.getIDCard());
                    employee.setWork_place(request.getPlaceOfWork());
                    employee.setUpdateAt(new Date());

                    employeeRepository.save(employee);
                    return new MessageOject("Success", "Profile update successfully",null);
                }


        return new MessageOject("Failed","Can not update profile",null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public List<JobDetail> viewOwnJob(int employeeID) {
       try {
            List<JobDetail> result = new ArrayList<>();
           List<Job> jobs = jobRepository.findByEmployeeId(employeeRepository.findById(employeeID));
           for (Job j: jobs) {
               JobDetail jd = viewDetailJob(j.getJobId());
               result.add(jd);
           }

           return result;

       } catch (Exception e) {
           throw new Exception(e.getMessage());
       }

}

    @SneakyThrows
    @Override
    public String toJSONJobs(int employeeID) {
        try {
            List<JobDetail> result = viewOwnJob(employeeID);

            ObjectMapper mapper = new ObjectMapper();
            String listAsJson = mapper.writeValueAsString(result);

            return  listAsJson;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}
