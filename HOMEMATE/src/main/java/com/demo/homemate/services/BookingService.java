package com.demo.homemate.services;

import com.demo.homemate.data.MailContents;
import com.demo.homemate.dtos.email.EmailDetails;
import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Job;
import com.demo.homemate.entities.Service;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.PaymentType;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.JobRepository;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IJobService;
import com.demo.homemate.utils.JobTimer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BookingService implements IJobService {

    private final JobRepository jobRepository;

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;

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
                emailDetails.setMsgBody(mailContents.CreateAccountSucess());

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
                job.setEnd(jobTimer.getTimeEnd(request.getTimeEnd()));
                job.setStatus(JobStatus.UN_PAY);
                job.setDescription(request.getJobDescription());
                job.setCreateAt(new Date());
                job.setUpdateAt(new Date());
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
                emailDetails.setMsgBody(mailContents.CreateAccountSucess());

                return new MessageOject("Success", "Booking completed", emailDetails);
            }
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null);
        }
    }
}
