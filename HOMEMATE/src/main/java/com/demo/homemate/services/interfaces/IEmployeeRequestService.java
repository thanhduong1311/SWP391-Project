package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.employeeCancelRequest.Response.CancelJobDetail;
import com.demo.homemate.dtos.employeeCancelRequest.Response.EmployeeCancelJobRequest;
import com.demo.homemate.dtos.notification.MessageObject;

import java.util.List;

public interface IEmployeeRequestService {

    public List<EmployeeCancelJobRequest> getRequestList() ;

    public CancelJobDetail getCancelJobDetailByRequest(int EmployeeRequestID) ;

    public MessageObject apporveRequest(int requestID);
    public MessageObject rejectRequest(int requestID);

    public MessageObject deleteRequest(int requestID);

}
