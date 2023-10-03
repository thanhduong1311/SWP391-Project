package com.demo.student.services;

import com.demo.student.dtos.student.request.CreateStudentRequest;
import com.demo.student.dtos.student.request.UpdateStudentRequest;
import com.demo.student.dtos.student.response.StudentResponse;
import com.demo.student.entities.Student;

import java.util.ArrayList;
import java.util.List;

public interface IStudentService {
    List<StudentResponse> getAllStudents();
    List<StudentResponse> getAllStudents(String code);

    StudentResponse getStudentById(int id);

    StudentResponse createStudent(CreateStudentRequest request);

    StudentResponse deleteStudent(int id);

    StudentResponse updateStudent(int id, UpdateStudentRequest request);

    Student getStudentByUsername(String username);

}