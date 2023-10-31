package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.customerReport.responese.CustomerReportJob;
import com.demo.homemate.dtos.notification.MessageOject;

import java.util.List;

public interface ICustomerReportService {
    public List<CustomerReportJob> getReportList() ;
    public MessageOject report(CustomerReportJob customerReportJob);
    public CustomerReportJob getReportDetailByID(int id);
    public CustomerReportJob getReportByJobID(int id);
    public MessageOject deleteReport(int id);

}
