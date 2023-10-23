package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Income;
import com.demo.homemate.entities.Job;

import java.util.List;

public interface IEmployeeService {
    List<JobDetail> getAvailableJob();

    JobDetail viewDetailJob(int jobId);

    MessageOject changePassword(ChangePasswordRequest request);

    List<JobDetail> getAvailableJobForSpecialEmployee(int employeeID);

    MessageOject takeJob(int jobID,int employeeID);

    MessageOject cancelJob(int JobID, int employeeID);

    MessageOject doneJob(int JobID,int employeeID);

    List<Income> getIncomes(int employeeId);



}
