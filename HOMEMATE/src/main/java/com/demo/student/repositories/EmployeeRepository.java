package com.demo.student.repositories;

import com.demo.student.entities.Customer;
import com.demo.student.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByEmail(String email);
}
