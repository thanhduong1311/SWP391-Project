package com.demo.homemate.repositories;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Job;
import com.demo.homemate.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {
    Job findById(int id);

    @Query("SELECT j FROM Job j WHERE j.customerId = :customer ORDER BY j.createAt DESC LIMIT 1")
    Job findFirstByCustomerIdAndCreateAtDesc(@Param("customer") Customer customer);

    @Query("SELECT j FROM Job j WHERE j.customerId = :customer")
    List<Job> findByCustomerId(@Param("customer") Customer customer);

    List<Job> findByStatus(JobStatus jobStatus);

}
