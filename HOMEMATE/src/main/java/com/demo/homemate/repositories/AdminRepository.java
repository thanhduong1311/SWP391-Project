package com.demo.homemate.repositories;

import com.demo.homemate.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findByUsername(String username);

}
