package com.demo.student.mappings;

import com.demo.student.dtos.student.request.CreateStudentRequest;
import com.demo.student.dtos.student.response.StudentResponse;
import com.demo.student.entities.Address;
import com.demo.student.entities.Student;
import org.springframework.stereotype.Component;

/**
 * map tu DTO -> Entity hoac nguoc lai
 */
@Component
public class StudentMappings {
    public StudentResponse toResponse(Student student) {
        if(student == null) {
            return null;
        }
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setCode(student.getCode());
        response.setDob(student.getDob());

        return response;
    }
    public Student toEntity(CreateStudentRequest request) {
        if(request == null){
            return null;
        }
        Student student = new Student();
        student.setName(request.getName());
        student.setCode(request.getCode());
        student.setDob(request.getDob());
        student.setUsername(request.getUsername());
        student.setPassword(request.getPassword());

        if(request.getAddress() != null) {
            Address address = new Address(request.getAddress());
            student.setAddress(address);
        }

        return student;
    }
}
