package com.demo.homemate.repositories;

import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Feedbacks;
import com.demo.homemate.entities.Income;
import com.demo.homemate.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedbacks, Integer> {

    Feedbacks findById(int feedbackID);
    @Query("SELECT fb FROM Feedbacks fb WHERE fb.jobId = :jobID")
    Feedbacks findFeedbackByJobID(@Param("jobID") int jobID);
}
