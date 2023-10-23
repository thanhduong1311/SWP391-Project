package com.demo.homemate.repositories;

import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Income;
import com.demo.homemate.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income,Integer> {
        @Query("SELECT i FROM Job i WHERE i.employeeId = :employee")
        List<Income> findByEmployeeId(@Param("employee") Employee employee);
}
