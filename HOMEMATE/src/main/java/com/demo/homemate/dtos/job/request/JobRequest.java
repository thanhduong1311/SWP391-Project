package com.demo.homemate.dtos.job.request;

import com.demo.homemate.enums.PaymentType;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {

    private int CustomerID;
    private String jobDescription;
    private Date date ;
    private String timeStart;
    private String timeEnd;
    private int serviceId;
    private int paymentType;
    private int paymentMethod;
    private double timeService;
    private long amount;
    private String serviceName;

}
