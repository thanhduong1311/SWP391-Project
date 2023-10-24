package com.demo.homemate.dtos.job.response;

import com.demo.homemate.enums.PaymentType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDetail {

    private int incomeId;
    private String customerName;
    private String address;
    private String serviceName;
    private double realAmount;
    private double amountFromJob;
    private double commission;
    private String customerPhone;
    private Date form;
    private Date to;
    private double totalTime;
    private Date dateOfIncome;
    private PaymentType paymentType;

    private String note;




}
