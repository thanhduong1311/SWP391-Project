package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.employeeCancelRequest.Response.CancelJobDetail;
import com.demo.homemate.dtos.employeeCancelRequest.Response.EmployeeCancelJobRequest;
import com.demo.homemate.dtos.notification.MessageOject;

import java.util.List;

public interface IEmployeeRequestService {

    public List<EmployeeCancelJobRequest> getRequestList() ;

    public CancelJobDetail getCancelJobDetailByRequest(int EmployeeRequestID) ;

    public MessageOject apporveRequest(int requestID);
    public MessageOject rejectRequest(int requestID);

    public MessageOject deleteRequest(int requestID);

}
