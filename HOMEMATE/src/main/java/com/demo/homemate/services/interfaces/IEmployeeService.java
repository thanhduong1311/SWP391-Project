package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.auth.request.ChangePasswordRequest;
import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.dtos.job.response.IncomeDetail;
import com.demo.homemate.dtos.job.response.JobDetail;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEmployeeService {
    List<JobDetail> getAvailableJob();

    JobDetail viewDetailJob(int jobId);

    MessageOject changePassword(ChangePasswordRequest request);

    List<JobDetail> getAvailableJobForSpecialEmployee(int employeeID);

    MessageOject takeJob(int jobID, int employeeID);

    MessageOject cancelJob(int JobID, String reason);

    MessageOject doneJob(int JobID, int employeeID);

    List<IncomeDetail> getIncomes(int employeeId);

    IncomeDetail getDetailIncome(int incomeId);

    String getScheduleJSON(int employeeID) ;


    MessageOject updateProfile(EmployeeProlife e, MultipartFile mf , String c);

    List<JobDetail> viewOwnJob(int employeeID);

    String toJSONJobs(int employeeID) ;
}
