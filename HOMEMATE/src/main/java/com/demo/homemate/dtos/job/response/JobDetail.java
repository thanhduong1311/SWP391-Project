package com.demo.homemate.dtos.job.response;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.PaymentType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobDetail {
    private int CustomerID;
    private int EmployeeID;
    private int JobID;

    private String customerName;
    private String employeeName;

    private String serviceName;
    private String phone;
    private String jobDescription;
    private String address;
    private double serviceTime;
    private PaymentType paymentType;
    private Date start;
    private Date end;
    private JobStatus status;

    private double price;

}
