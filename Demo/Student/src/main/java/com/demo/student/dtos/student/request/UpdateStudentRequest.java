package com.demo.student.dtos.student.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentRequest {
    private String name;

    private String code;

    private LocalDate dob;

    private String password;
}
