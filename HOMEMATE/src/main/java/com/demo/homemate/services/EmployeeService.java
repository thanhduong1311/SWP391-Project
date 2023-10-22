package com.demo.homemate.services;

import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Job;
import com.demo.homemate.entities.Service;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.repositories.JobRepository;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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

    @SneakyThrows
    @Override
    public List<JobDetail> getAvailableJob() {
        try {
            List<JobDetail> availableJob = new ArrayList<>();

            List<Job> jobs = jobRepository.findByStatus(JobStatus.AVAILABLE);

            for (Job j : jobs) {
                JobDetail jobOverView = new JobDetail();
                Customer customer = new Customer();

                jobOverView.setCustomerName(customer.getFullName());
                jobOverView.setServiceName(j.getServiceId().getName());
                jobOverView.setPrice(paymentService.getTotalMoney(paymentService.getTotalTime(j.getStart(),j.getEnd()),j.getServiceId().getServiceId()));
                jobOverView.setStatus(j.getStatus());
                jobOverView.setJobID(j.getJobId());

                availableJob.add(jobOverView);
            }


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
}
