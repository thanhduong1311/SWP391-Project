package com.demo.homemate.dtos.customerReport.responese;


import com.demo.homemate.entities.Customer;
import com.demo.homemate.enums.PaymentType;
import com.demo.homemate.enums.RequestStatus;
import com.demo.homemate.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerReportJob {
    private int reportId;
    private int jobId;
    private int CustomerId;
    private int employeeID;
    private String account;
    private String CustomerName;
    private String employeeName;
    private String reason;
    private String address;
    private Date start;
    private Date end;
    private String service;
    private String description;
    private Role role;
}
