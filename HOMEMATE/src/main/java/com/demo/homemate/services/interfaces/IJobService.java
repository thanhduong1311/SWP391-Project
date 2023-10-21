package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.notification.MessageOject;

public interface IJobService {
    MessageOject createJob(JobRequest request);

    MessageOject updateJob(JobRequest request);

    MessageOject cancelJob(int jobID) ;

    MessageOject createJobWithoutPayment(JobRequest request);

    MessageOject completeCreateJob(int customerId);
}
