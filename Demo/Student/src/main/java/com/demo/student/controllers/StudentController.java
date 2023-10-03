package com.demo.student.controllers;

import com.demo.student.dtos.student.request.CreateStudentRequest;
import com.demo.student.dtos.student.request.UpdateStudentRequest;
import com.demo.student.dtos.student.response.StudentResponse;
import com.demo.student.entities.Student;
import com.demo.student.services.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
@Slf4j
public class StudentController {
    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents(@RequestParam(required = false) String code) {
        return ResponseEntity.ok(studentService.getAllStudents(code));
    }
    @GetMapping("{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable int id){
        StudentResponse serviceResponse = studentService.getStudentById(id);
        if(serviceResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody CreateStudentRequest request ) throws Exception {
        try {
            StudentResponse response = studentService.createStudent(request);
            return ResponseEntity.ok(response);
        }catch (Exception ex) {
            //ex.printStackTrace();
            log.error("Internal service err" + ex.getMessage());
            return  ResponseEntity.internalServerError().build();
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<StudentResponse> deleteStudentById(@PathVariable int id){
        StudentResponse existed = studentService.getStudentById(id);
        if(existed == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok(existed);
    }
    @PutMapping("{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable int id, @RequestBody UpdateStudentRequest request) {
        StudentResponse existed = studentService.getStudentById(id);
        if(existed == null) {
            return ResponseEntity.notFound().build();
        }
        StudentResponse studentResponse = studentService.updateStudent(id, request);
        return ResponseEntity.ok(studentResponse);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

            Student student = studentService.getStudentByUsername(username);
            if (username.equals(student.getUsername()) && password.equals(student.getPassword())) {
                return "Login success";
            } else {
                return "Login failed";
            }
    }


}
