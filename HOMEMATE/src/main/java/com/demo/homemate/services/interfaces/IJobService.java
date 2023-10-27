package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageObject;

import java.util.List;

public interface IJobService {
    MessageObject createJob(JobRequest request);

    MessageObject updateJob(JobRequest request);

    MessageObject cancelJob(int jobID) ;

    MessageObject createJobWithoutPayment(JobRequest request);

    MessageObject completeCreateJob(int customerId);

    List<JobDetail> getCustomerBookings(int customerID);

    JobDetail getAJob(int jobID);

}
