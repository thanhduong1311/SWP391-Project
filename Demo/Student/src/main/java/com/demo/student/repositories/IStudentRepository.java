package com.demo.student.repositories;

import com.demo.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {

    Student findFirstByCode(String code);

    Student findFirstByName(String name);

    Student findStudentByUsername(String username);


    Student findFirstByCodeAndPassword(String code, String password);

    List<Student> findByDobIsAfterAndDobIsBefore(LocalDate after, LocalDate before);

    List<Student> findByNameContainsOrCodeContains(String query1, String query2);

    List<Student> findByCodeContainsIgnoreCase(String code);


}
