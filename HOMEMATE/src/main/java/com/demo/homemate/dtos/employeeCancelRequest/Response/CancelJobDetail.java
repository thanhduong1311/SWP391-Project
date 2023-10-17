package com.demo.homemate.dtos.employeeCancelRequest.Response;

import com.demo.homemate.enums.PaymentType;
import com.demo.homemate.enums.RequestStatus;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CancelJobDetail {
    private int requestId;
    private int jobId;
    private int CustomerId;
    private String CustomerName;
    private String EmployeeName;
    private String reason;
    private RequestStatus status;
    private String address;
    private PaymentType paymentType;
    private Date start;
    private Date end;
    private String service;
    private String description;



}
