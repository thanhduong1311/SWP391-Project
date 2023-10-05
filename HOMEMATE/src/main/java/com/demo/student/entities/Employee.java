package com.demo.student.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = Employee.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Employee extends BaseAccount{

    public static final String COLLECTION_NAME = "employee";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    private String idCardNumber;

}
