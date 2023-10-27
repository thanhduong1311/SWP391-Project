package com.demo.homemate.services;

import com.demo.homemate.data.MailContents;
import com.demo.homemate.dtos.email.EmailDetails;
import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageObject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Job;
import com.demo.homemate.entities.Service;
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

    @Override
    public MessageObject createJob(JobRequest request) {
        try {

            Customer customer = customerRepository.findById(request.getCustomerID());
            Service service = serviceRepository.findById(request.getServiceId());

            if(customer.getAccountStatus().ordinal() == 1) {
                return new MessageObject("Failed", "Your account is blocked, can not making this booking", null);
            } else {
                Job job = new Job();
                JobTimer jobTimer = new JobTimer();
                job.setCustomerId(customer);
                job.setServiceId(service);
                job.setPaymentType(request.getPaymentType() == 0 ? PaymentType.BANKING:PaymentType.CASH);
                job.setStart(jobTimer.getTimeStart(request.getTimeStart()));
                job.setEnd(jobTimer.getTimeEnd(request.getTimeEnd()));
                job.setStatus(JobStatus.AVAILABLE);
                job.setDescription(request.getJobDescription());
                job.setCreateAt(new Date());
                job.setUpdateAt(new Date());
                jobRepository.save(job);



                MailContents mailContents = new MailContents();
                mailContents.setSubjectName(customer.getFullName());
                mailContents.setTitle("[HOMEMATE] BOOKING SUCCESSFULLY");


                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(customer.getEmail());
                emailDetails.setSubject(mailContents.getTitle());
                emailDetails.setMsgBody(mailContents.bookingSuccess());

                return new MessageObject("Success","Booking completed",emailDetails);

            }
        } catch (Exception e) {
            return new MessageObject("Failed","Can not create booking, try again",null);
        }
    }

    @Override
    public MessageObject updateJob(JobRequest request) {
        return null;
    }

    @Override
    public MessageObject cancelJob(int jobID) {
        return null;
    }

    @Override
    public MessageObject createJobWithoutPayment(JobRequest request) {
        try {

            Customer customer = customerRepository.findById(request.getCustomerID());
            Service service = serviceRepository.findById(request.getServiceId());

            if(customer.getAccountStatus().ordinal() == 1) {
                return new MessageObject("Failed", "Your account is blocked, can not making this booking", null);
            } else {
                Job job = new Job();
                JobTimer jobTimer = new JobTimer();
                job.setCustomerId(customer);
                job.setServiceId(service);
                job.setPaymentType(request.getPaymentType() == 0 ? PaymentType.BANKING:PaymentType.CASH);
                job.setStart(jobTimer.getTimeStart(request.getTimeStart()));
                job.setEnd(jobTimer.getTimeEnd(request.getTimeEnd()));
                job.setStatus(JobStatus.UN_PAY);
                job.setDescription(request.getJobDescription());
                job.setCreateAt(new Date());
                job.setUpdateAt(new Date());
                jobRepository.save(job);

                return new MessageObject("Success",null,null);

            }
        } catch (Exception e) {
            return new MessageObject("Failed","Can not create booking, try again",null);
        }
    }


    @Override
    public MessageObject completeCreateJob(int customerId) {
        try {
            Customer customer = customerRepository.findById(customerId);

            Job job = jobRepository.findFirstByCustomerIdAndCreateAtDesc(customer);

            if(job == null) {
                return new MessageObject("Failed","There some error when create booking, We will contact to you as soon as possible ",null);
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

                return new MessageObject("Success", "Booking completed", emailDetails);
            }
        } catch (Exception e) {
            return new MessageObject("Failed",e.getMessage(),null);
        }
    }

    @SneakyThrows
    @Override
    public List<JobDetail> getCustomerBookings(int customerID) {
        List<JobDetail> bookings = new ArrayList<>();
        try {
            Customer customer = customerRepository.findById(customerID);
            List<Job> jobs = jobRepository.findByCustomerId(customer);


            for (Job j : jobs) {
                JobDetail jobOverView = new JobDetail();

                Employee employee = employeeRepository.findById(j.getEmployeeId() == null ? new Employee().getEmployeeId():j.getEmployeeId().getEmployeeId());
                jobOverView.setServiceName(j.getServiceId().getName());
                jobOverView.setPrice(paymentService.getTotalMoney(paymentService.getTotalTime(j.getStart(),j.getEnd()),j.getServiceId().getServiceId()));
                jobOverView.setEmployeeName(employee == null ? "":employee.getFullName());
                jobOverView.setStatus(j.getStatus());
                jobOverView.setJobID(j.getJobId());

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
            jobDetail.setAddress(customer.getAddress_detail() + ", " + customer.getDistrict() + ", " + customer.getCity());
            jobDetail.setStart(job.getStart());
            jobDetail.setEnd(job.getEnd());
            jobDetail.setPhone(customer.getPhone());
            jobDetail.setJobDescription(job.getDescription());
            jobDetail.setPaymentType(job.getPaymentType());
            jobDetail.setStatus(job.getStatus());

            jobDetail.setServiceTime(paymentService.getTotalTime(job.getStart(),job.getEnd()));
            jobDetail.setPrice(paymentService.getTotalMoney(paymentService.getTotalTime(job.getStart(),job.getEnd()),job.getServiceId().getServiceId()));

            return jobDetail;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
