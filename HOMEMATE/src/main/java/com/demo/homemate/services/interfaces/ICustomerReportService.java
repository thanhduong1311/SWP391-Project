package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.customerReport.responese.CustomerReportJob;
import com.demo.homemate.dtos.notification.MessageObject;

import java.util.List;

public interface ICustomerReportService {
    public List<CustomerReportJob> getReportList() ;

    public CustomerReportJob getReportDetailByID(int id);

    public MessageObject deleteReport(int id);

}
