package com.demo.homemate.repositories;

import com.demo.homemate.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByUsername(String username);
    Customer findByEmail(String email);

    Customer findById(int Id);


}
