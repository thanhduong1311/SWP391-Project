package com.demo.homemate.services;

import com.demo.homemate.data.MailContents;
import com.demo.homemate.dtos.email.EmailDetails;
import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.*;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.PaymentType;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.repositories.JobRepository;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IJobService;
import com.demo.homemate.utils.JobTimer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BookingService implements IJobService {

    private final JobRepository jobRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final ServiceRepository serviceRepository;

    private final PaymentService paymentService;

    private final RankingService rankingService;


    @Override
    public MessageOject createJob(JobRequest request) {
        try {

            Customer customer = customerRepository.findById(request.getCustomerID());
            Service service = serviceRepository.findById(request.getServiceId());

            if(customer.getAccountStatus().ordinal() == 1) {
                return new MessageOject("Failed", "Your account is blocked, can not making this booking", null);
            } else {
                Job job = new Job();
                JobTimer jobTimer = new JobTimer();
                job.setCustomerId(customer);
                job.setServiceId(service);
                job.setPaymentType(request.getPaymentType() == 0 ? PaymentType.BANKING:PaymentType.CASH);
                job.setStart(jobTimer.getTimeStart(request.getTimeStart()));
                job.setEnd(jobTimer.getEndTime(request.getTimeStart(),request.getTimeService()));
                job.setStatus(JobStatus.AVAILABLE);
                job.setDescription(request.getJobDescription());
                job.setCreateAt(new Date());
                job.setUpdateAt(new Date());
                job.setLocation(request.getLocation());
                job.setJobAddress(request.getCustomerAddress());
                jobRepository.save(job);
                MailContents mailContents = new MailContents();
                mailContents.setSubjectName(customer.getFullName());
                mailContents.setTitle("[HOMEMATE] BOOKING SUCCESSFULLY");


                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(customer.getEmail());
                emailDetails.setSubject(mailContents.getTitle());
                emailDetails.setMsgBody(mailContents.bookingSuccess());

                return new MessageOject("Success","Booking completed",emailDetails);

            }
        } catch (Exception e) {
            return new MessageOject("Failed","Can not create booking, try again",null);
        }
    }

    @Override
    public MessageOject updateJob(JobRequest request) {
        return null;
    }

    @Override
    public MessageOject cancelJob(int jobID) {
        return null;
    }

    @Override
    public MessageOject createJobWithoutPayment(JobRequest request) {
        try {

            Customer customer = customerRepository.findById(request.getCustomerID());
            Service service = serviceRepository.findById(request.getServiceId());

            if(customer.getAccountStatus().ordinal() == 1) {
                return new MessageOject("Failed", "Your account is blocked, can not making this booking", null);
            } else {
                Job job = new Job();
                JobTimer jobTimer = new JobTimer();
                job.setCustomerId(customer);
                job.setServiceId(service);
                job.setPaymentType(request.getPaymentType() == 0 ? PaymentType.BANKING:PaymentType.CASH);
                job.setStart(jobTimer.getTimeStart(request.getTimeStart()));
                job.setEnd(jobTimer.getEndTime(request.getTimeStart(),request.getTimeService()));
                job.setStatus(JobStatus.UN_PAY);
                job.setDescription(request.getJobDescription());
                job.setCreateAt(new Date());
                job.setUpdateAt(new Date());
                job.setLocation(request.getLocation());
                job.setJobAddress(request.getCustomerAddress());
                jobRepository.save(job);
                return new MessageOject("Success",null,null);

            }
        } catch (Exception e) {
            return new MessageOject("Failed","Can not create booking, try again",null);
        }
    }

    @Override
    public MessageOject completeCreateJob(int customerId) {
        try {
            Customer customer = customerRepository.findById(customerId);

            Job job = jobRepository.findFirstByCustomerIdAndCreateAtDesc(customer);

            if(job == null) {
                return new MessageOject("Failed","There some error when create booking, We will contact to you as soon as possible ",null);
            } else {
                job.setStatus(JobStatus.AVAILABLE);
                jobRepository.save(job);



                MailContents mailContents = new MailContents();
                mailContents.setSubjectName(customer.getFullName());
                mailContents.setTitle("[HOMEMATE] BOOKING SUCCESSFULLY");
                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(customer.getEmail());
                emailDetails.setSubject(mailContents.getTitle());
                emailDetails.setMsgBody(mailContents.bookingSuccess());

                return new MessageOject("Success", "Booking completed", emailDetails);
            }
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null);
        }
    }
    @SneakyThrows
    @Override
    public List<JobDetail> getCustomerBookings(int customerID) {
        List<JobDetail> bookings = new ArrayList<>();
        try {
            Customer customer = customerRepository.findById(customerID);
            List<Job> jobs = jobRepository.findByCustomerId(customer);
            Ranking ranking = rankingService.getRank(customer.getUsername());
            if (ranking==null){
                ranking = new Ranking();
               ranking.setRankId(0);
            }
            for (Job j : jobs) {
                JobDetail jobOverView = new JobDetail();
                Employee employee = employeeRepository.findById(j.getEmployeeId() == null ? new Employee().getEmployeeId():j.getEmployeeId().getEmployeeId());
                jobOverView.setServiceName(j.getServiceId().getName());
                jobOverView.setPrice(paymentService.getDiscountedFinalMoney(paymentService.getTotalTime(j.getStart(),j.getEnd()),j.getServiceId().getServiceId(),ranking.getRankId()));
                jobOverView.setEmployeeName(employee == null ? "":employee.getFullName());
                jobOverView.setStatus(j.getStatus());
                jobOverView.setJobID(j.getJobId());
                jobOverView.setEmployeeAvt(employee == null ? "":employee.getAvatar());
                jobOverView.setJobInCalendar(JobTimer.convertDateToString(j.getStart()));
                jobOverView.setAddress(j.getJobAddress());
                jobOverView.setAddress(j.getJobAddress());
                bookings.add(jobOverView);
            }

            return  bookings;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public JobDetail getAJob(int jobID) {
        try {
            Job job = jobRepository.findById(jobID);
            Customer customer = customerRepository.findById(job.getCustomerId().getCustomerId());
            Service service = serviceRepository.findById(job.getServiceId().getServiceId());
            Employee employee = employeeRepository.findById(job.getEmployeeId() == null ? new Employee().getEmployeeId():job.getEmployeeId().getEmployeeId());
            Ranking ranking = rankingService.getRank(customer.getUsername());
            if (ranking==null){
                ranking = new Ranking();
                ranking.setRankId(0);
            }
            JobDetail jobDetail = new JobDetail();

            if(employee != null ) {
                jobDetail.setEmployeeID(employee.getEmployeeId());
                jobDetail.setEmployeeName(employee.getFullName());
                jobDetail.setEmployeeAvt(employee.getAvatar());
            }
            jobDetail.setCustomerID(customer.getCustomerId());
            jobDetail.setJobID(jobID);
            jobDetail.setCustomerName(customer.getFullName());
            jobDetail.setServiceName(service.getName());
            jobDetail.setAddress(job.getJobAddress());
            jobDetail.setStart(job.getStart());
            jobDetail.setEnd(job.getEnd());
            jobDetail.setPhone(customer.getPhone());
            jobDetail.setJobDescription(job.getDescription());
            jobDetail.setPaymentType(job.getPaymentType());
            jobDetail.setStatus(job.getStatus());
            jobDetail.setJobLocation(job.getLocation());
            jobDetail.setServiceTime(paymentService.getTotalTime(job.getStart(),job.getEnd()));
            jobDetail.setPrice(paymentService.getTotalMoney(paymentService.getTotalTime(job.getStart(),job.getEnd()),job.getServiceId().getServiceId()));
            jobDetail.setPrice(paymentService.getDiscountedFinalMoney(paymentService.getTotalTime(job.getStart(),job.getEnd()),job.getServiceId().getServiceId(),ranking.getRankId()));
            return jobDetail;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
