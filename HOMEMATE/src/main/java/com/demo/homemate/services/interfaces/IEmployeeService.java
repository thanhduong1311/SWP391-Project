package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.dtos.job.response.IncomeDetail;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageObject;

import java.util.List;

public interface IEmployeeService {
    List<JobDetail> getAvailableJob();

    JobDetail viewDetailJob(int jobId);

    MessageObject changePassword(ChangePasswordRequest request);

    List<JobDetail> getAvailableJobForSpecialEmployee(int employeeID);

    MessageObject takeJob(int jobID, int employeeID);

    MessageObject cancelJob(int JobID, String reason);

    MessageObject doneJob(int JobID, int employeeID);

    List<IncomeDetail> getIncomes(int employeeId);

    IncomeDetail getDetailIncome(int incomeId);

    String getScheduleJSON(int employeeID) ;


    MessageObject updateProfile(EmployeeProlife request);

    List<JobDetail> viewOwnJob(int employeeID);

    String toJSONJobs(int employeeID) ;
}
