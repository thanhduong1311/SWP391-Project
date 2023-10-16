package com.demo.homemate.dtos.customerReport.responese;


import com.demo.homemate.entities.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerReportJob {
    private String customerName;
    private String employeeName;
    private int employeeID;
    private String reason;

}
