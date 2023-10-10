package com.demo.homemate.repositories;

import com.demo.homemate.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByUsername(String username);
    Employee findByEmail(String email);
    Employee findById(int id);
}
