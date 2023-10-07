package com.demo.homemate.repositories;

import com.demo.homemate.entities.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedbacks, Integer> {
}
