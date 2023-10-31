package com.demo.homemate.repositories;

import com.demo.homemate.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Integer> {

        @Query("SELECT m " +
                "FROM Member m " +
                "WHERE m.customer.customerId = :customerID " +
                "ORDER BY m.upRank_date DESC " +
                "limit 1")
        Member findByCustomerID(@Param("customerID") int customerID);

}
