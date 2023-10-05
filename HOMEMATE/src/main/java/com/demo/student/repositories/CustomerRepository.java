package com.demo.student.repositories;

import com.demo.student.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);


}
