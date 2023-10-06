package com.demo.homemate.repositories;

import com.demo.homemate.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByUsername(String username);
    Employee findByEmail(String email);
}
