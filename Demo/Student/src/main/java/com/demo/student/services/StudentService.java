package com.demo.student.services;

import com.demo.student.dtos.student.request.CreateStudentRequest;
import com.demo.student.dtos.student.request.UpdateStudentRequest;
import com.demo.student.dtos.student.response.StudentResponse;
import com.demo.student.entities.Student;
import com.demo.student.mappings.StudentMappings;
import com.demo.student.repositories.IStudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("studentService")
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final IStudentRepository studentRepository;
    private final StudentMappings mappings;

    public List<StudentResponse> getAllStudents() {
        //xu ly logic

        List<Student> students = studentRepository.findAll();
        List<StudentResponse> responses = new ArrayList<>();

        for(Student student: students) {
            responses.add(mappings.toResponse((student)));
        }
        return responses;
    }

    @Override
    public List<StudentResponse> getAllStudents(String code) {
        List<Student> students = new ArrayList<>();
        if(code != null) {
            students =  studentRepository.findByCodeContainsIgnoreCase(code);
        } else {
            students = studentRepository.findAll();

        };
        List<StudentResponse> responses = new ArrayList<>();

        for(Student student: students) {
            responses.add(mappings.toResponse((student)));

        }
        return responses;
    }

    public  StudentResponse getStudentById(int id) {
        //logic

        Optional<Student> studentOptional  = studentRepository.findById(id);
        if(studentOptional.isPresent()) {
            return mappings.toResponse((studentOptional.get()));
        }else {
            return null;
        }

    }
    @SneakyThrows //thay throw ex
    public StudentResponse createStudent(CreateStudentRequest request) {
        //xu ly logic

        //Mapping tu dto -> entity
        Student student = mappings.toEntity(request);

        //Luu vao DB
        Student savedStudent = studentRepository.save(student);
        return mappings.toResponse(savedStudent);
    }

    public StudentResponse deleteStudent(int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            studentRepository.delete(student);

            return mappings.toResponse(student);
        }
        return null;
    }


    public StudentResponse updateStudent(int id, UpdateStudentRequest request) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setName(request.getName());
            student.setCode(request.getCode());
            student.setDob(request.getDob());
            student.setPassword(request.getPassword());

            studentRepository.save(student);

            return mappings.toResponse(student);
        }
        return null;
    }

    public Student getStudentByUsername(String username) {
        Student s = null;
        s = studentRepository.findStudentByUsername(username);
        return s;
    }

}
