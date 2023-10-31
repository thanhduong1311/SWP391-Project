package com.demo.homemate.repositories;

import com.demo.homemate.entities.Feedbacks;
import com.demo.homemate.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
    Report findById(int id);
    @Query("SELECT r FROM Report r WHERE r.jobId.jobId = :jobID")
    Report findReportByJobID(@Param("jobID") int jobID);
}
