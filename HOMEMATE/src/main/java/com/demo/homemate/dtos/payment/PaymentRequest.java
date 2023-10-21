package com.demo.homemate.dtos.payment;


import com.demo.homemate.dtos.job.request.JobRequest;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private int customerID;
    private long amount;
    private String paymentType;

}
