package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.job.response.JobDetail;

import java.util.List;

public interface IEmployeeService {
    List<JobDetail> getAvailableJob();

    JobDetail viewDetailJob(int jobId);

}
