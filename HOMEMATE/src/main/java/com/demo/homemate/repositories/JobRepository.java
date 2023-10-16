package com.demo.homemate.repositories;

import com.demo.homemate.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {
    Job findById(int id);
}
