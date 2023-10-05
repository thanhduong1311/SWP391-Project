package com.demo.student.repositories;

import com.demo.student.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findByUsername(String username);

}
