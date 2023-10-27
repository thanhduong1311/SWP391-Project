package com.demo.homemate.services;

import com.demo.homemate.dtos.employeeCancelRequest.Response.CancelJobDetail;
import com.demo.homemate.dtos.employeeCancelRequest.Response.EmployeeCancelJobRequest;
import com.demo.homemate.dtos.notification.MessageObject;
import com.demo.homemate.entities.*;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.RequestStatus;
import com.demo.homemate.repositories.*;
import com.demo.homemate.services.interfaces.IEmployeeRequestService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class EmployeeRequestService implements IEmployeeRequestService {

    private final EmployeeRequestRepository employeeRequestRepository;

    private final EmployeeRepository employeeRepository;

    private final JobRepository jobRepository;

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;

    private final PaymentService paymentService;


    @Override
    public List<EmployeeCancelJobRequest> getRequestList() {
        List<EmployeeCancelJobRequest> result = new ArrayList<>();

        try {
            List<EmployeeRequest> list = employeeRequestRepository.findAll();
            for (EmployeeRequest er:
                    list) {
                EmployeeCancelJobRequest ecr = new EmployeeCancelJobRequest();
                ecr.setRequestID(er.getRequestId());
                ecr.setRequestStatus(er.getStatus());
                ecr.setAccountName(employeeRepository.findById(er.getEmployeeId().getEmployeeId()).getFullName());
                ecr.setAccount(employeeRepository.findById(er.getEmployeeId().getEmployeeId()).getUsername());
                ecr.setRole(employeeRepository.findById(er.getEmployeeId().getEmployeeId()).getRole());
                ecr.setReason(er.getReason());
                ecr.setRequestStatus(er.getStatus());
                ecr.setJobId(er.getJobId().getJobId());
                result.add(ecr);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }



    @Override
    public CancelJobDetail getCancelJobDetailByRequest(int id) {

        EmployeeRequest employeeCancelJobRequest = employeeRequestRepository.findById(id);

        CancelJobDetail cjd = new CancelJobDetail();
        try {
            Job job = jobRepository.findById(employeeCancelJobRequest.getJobId().getJobId());
            Service service = serviceRepository.findById(job.getServiceId().getServiceId());
            Employee employee = employeeRepository.findById(job.getEmployeeId().getEmployeeId());
            Customer customer = customerRepository.findById(job.getCustomerId().getCustomerId());

            cjd.setRequestId(id);
            cjd.setCustomerId(customer.getCustomerId());
            cjd.setEmployeeName(employee.getFullName());
            cjd.setCustomerName(customer.getFullName());
            cjd.setService(service.getName());
            cjd.setJobId(job.getJobId());
            cjd.setPaymentType(job.getPaymentType());
            cjd.setStart(job.getStart());
            cjd.setEnd(job.getEnd());
            cjd.setAddress(customer.getAddress_detail());
            cjd.setDescription(job.getDescription());
            cjd.setReason(employeeCancelJobRequest.getReason());
            cjd.setStatus(employeeCancelJobRequest.getStatus());

        } catch (Exception e) {
            throw e;
        }
        return cjd;
    }

    @Override
    public MessageObject apporveRequest(int requestID) {
       try {
           EmployeeRequest er = employeeRequestRepository.findById(requestID);
           if(er == null) {
               return new MessageObject("Failed", "Can not approve this request",null);
           } else {
               Job job = jobRepository.findById(employeeRequestRepository.findById(requestID).getJobId().getJobId());
               Employee employee = employeeRepository.findById(employeeRequestRepository.findById(requestID).getEmployeeId().getEmployeeId());
               EmployeeRequest request = employeeRequestRepository.findById(requestID);
               Customer customer = customerRepository.findById(jobRepository.findById(employeeRequestRepository.findById(requestID).getJobId().getJobId()).getCustomerId().getCustomerId());

               double totalTime = paymentService.getTotalTime(job.getStart(),job.getEnd());
               double rawPrice = paymentService.getTotalMoney(totalTime,job.getServiceId().getServiceId());
               double realMoney = rawPrice -rawPrice*0.02;

                if(job.getPaymentType().ordinal() == 0) {

                    double money = customer.getBalance() + rawPrice;

                    BigDecimal bd = new BigDecimal(money);
                    bd = bd.setScale(4, RoundingMode.HALF_UP);
                    money = bd.doubleValue();

                    customer.setBalance(money);
                    customerRepository.save(customer);
                }

               if(job.getPaymentType().ordinal() == 1) {

                   double money =employee.getBalance() - rawPrice*0.02;

                   BigDecimal bd = new BigDecimal(money);
                   bd = bd.setScale(4, RoundingMode.HALF_UP);
                   money = bd.doubleValue();

                   employee.setBalance(money);
                   employeeRepository.save(employee);
               }

               job.setStatus(JobStatus.CANCEL);

               er.setDecisionAt(new Date());
               er.setStatus(RequestStatus.APPROVED);
               employeeRequestRepository.save(er);
               jobRepository.save(job);
               return new MessageObject("Success", "Request is approved",null);
           }
       } catch (Exception e) {
           throw e;
       }
    }

    @Override
    public MessageObject rejectRequest(int requestID) {
        try {
            EmployeeRequest er = employeeRequestRepository.findById(requestID);
            if(er == null) {
                return new MessageObject("Failed", "Can not reject this request",null);
            } else {
                er.setDecisionAt(new Date());
                er.setStatus(RequestStatus.REJECTED);
                employeeRequestRepository.save(er);
                return new MessageObject("Success", "Request is rejected",null);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public MessageObject deleteRequest(int requestID) {
        try {
            EmployeeRequest er = employeeRequestRepository.findById(requestID);
            if(er == null) {
                return new MessageObject("Failed", "Can not delete this request",null);
            } else {
                employeeRequestRepository.delete(er);
                return new MessageObject("Success", "Request is deleted",null);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
