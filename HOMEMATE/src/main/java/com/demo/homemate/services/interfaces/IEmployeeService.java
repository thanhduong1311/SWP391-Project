package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;

import java.util.List;

public interface IEmployeeService {
    List<JobDetail> getAvailableJob();

    JobDetail viewDetailJob(int jobId);

    MessageOject changePassword(ChangePasswordRequest request);

}
