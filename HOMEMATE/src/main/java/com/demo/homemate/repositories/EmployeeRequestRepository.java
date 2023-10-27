package com.demo.homemate.repositories;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.EmployeeRequest;
import com.demo.homemate.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRequestRepository extends JpaRepository<EmployeeRequest,Integer> {
    EmployeeRequest findById(int id);

    @Query("SELECT e FROM EmployeeRequest e WHERE e.jobId = :job")
    EmployeeRequest findByJobId(@Param("job") Job job);

}
