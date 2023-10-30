package com.demo.homemate.services;

import com.demo.homemate.dtos.customerReport.responese.CustomerReportJob;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.*;
import com.demo.homemate.repositories.*;
import com.demo.homemate.services.interfaces.ICustomerReportService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CustomerReportService implements ICustomerReportService {

    private final ReportRepository reportRepository;

    private final JobRepository jobRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final ServiceRepository serviceRepository;



    @Override
    public List<CustomerReportJob> getReportList() {
        List<CustomerReportJob> result = new ArrayList<>();
        try {
            List<Report> reports = reportRepository.findAll();
            for (Report r: reports
            ) {
                CustomerReportJob crj = new CustomerReportJob();
                crj.setReportId(r.getReportId());
                crj.setCustomerName(customerRepository.findById(r.getCustomerId().getCustomerId()).getFullName());
                crj.setAccount(customerRepository.findById(r.getCustomerId().getCustomerId()).getUsername());
                crj.setReason(r.getReason());
                crj.setEmployeeName(employeeRepository.findById(jobRepository.findById(r.getJobId().getJobId()).getEmployeeId().getEmployeeId()).getFullName());
                crj.setEmployeeID(employeeRepository.findById(jobRepository.findById(r.getJobId().getJobId()).getEmployeeId().getEmployeeId()).getEmployeeId());
                crj.setRole(employeeRepository.findById(r.getCustomerId().getCustomerId()).getRole());
                result.add(crj );
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    @Override
    public CustomerReportJob getReportDetailByID(int id) {
        Report report  = reportRepository.findById(id);
        CustomerReportJob cjd = new CustomerReportJob();
        try {
            Job job = jobRepository.findById(report.getJobId().getJobId());
            Service service = serviceRepository.findById(job.getServiceId().getServiceId());
            Employee employee = employeeRepository.findById(job.getEmployeeId().getEmployeeId());
            Customer customer = customerRepository.findById(job.getCustomerId().getCustomerId());
            cjd.setReportId(id);
            cjd.setEmployeeID(employee.getEmployeeId());
            cjd.setCustomerId(customer.getCustomerId());
            cjd.setEmployeeName(employee.getFullName());
            cjd.setCustomerName(customer.getFullName());
            cjd.setService(service.getName());
            cjd.setJobId(job.getJobId());
            cjd.setStart(job.getStart());
            cjd.setEnd(job.getEnd());
            cjd.setAddress(customer.getAddress_detail());
            cjd.setDescription(job.getDescription());
            cjd.setReason(report.getReason());
        } catch (Exception e) {
            throw e;
        }
        return cjd;
    }
    @Override
    public CustomerReportJob getReportByJobID(int id) {
        Job job  = jobRepository.findById(id);
        CustomerReportJob cjd = new CustomerReportJob();
        if (job==null){
            return null;
        }
        if(job.getReport()!=null){
            cjd.setReason(job.getReport().getReason());
        }
        try {
            Service service = serviceRepository.findById(job.getServiceId().getServiceId());
            Employee employee = employeeRepository.findById(job.getEmployeeId().getEmployeeId());
            Customer customer = customerRepository.findById(job.getCustomerId().getCustomerId());
            cjd.setReportId(id);
            cjd.setEmployeeID(employee.getEmployeeId());
            cjd.setCustomerId(customer.getCustomerId());
            cjd.setEmployeeName(employee.getFullName());
            cjd.setCustomerName(customer.getFullName());
            cjd.setService(service.getName());
            cjd.setJobId(job.getJobId());
            cjd.setStart(job.getStart());
            cjd.setEnd(job.getEnd());
            cjd.setAddress(customer.getAddress_detail());
            cjd.setDescription(job.getDescription());
        } catch (Exception e) {
            throw e;
        }
        return cjd;
    }
    @Override
    public MessageOject deleteReport(int id) {
        try {
            Report report = reportRepository.findById(id);
            if(report == null) {
                return new MessageOject("Failed", "Can not delete this report",null);
            } else {
                reportRepository.delete(report);
                return new MessageOject("Success", "Report is deleted",null);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
