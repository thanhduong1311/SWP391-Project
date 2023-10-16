package com.demo.homemate.dtos.job.request;

import lombok.*;

import java.time.LocalTime;
import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {
    private String Customer;
    private String jobDescription;
    private Date date ;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private int paymentType;
}
