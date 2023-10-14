package com.demo.homemate.repositories;

import com.demo.homemate.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    Service findById(int serviceID);

    Service findByName(String name);

}
