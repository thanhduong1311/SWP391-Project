package com.demo.homemate.repositories;

import com.demo.homemate.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Ranking,Integer> {

        @Query("SELECT m FROM Member m WHERE m.customer = :customer")
        Member findByCustomer(@Param("customer") Customer customer);

}
