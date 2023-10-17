package com.demo.homemate.repositories;

import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.EmployeeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRequestRepository extends JpaRepository<EmployeeRequest,Integer> {
    EmployeeRequest findById(int id);

}
