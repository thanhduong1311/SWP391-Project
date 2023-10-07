package com.demo.homemate.repositories;

import com.demo.homemate.entities.EmployeeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRequestRepository extends JpaRepository<EmployeeRequest,Integer> {
}
