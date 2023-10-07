package com.demo.homemate.entities;

import com.demo.homemate.enums.Role;
import com.demo.homemate.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = Employee.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Employee{

    public static final String COLLECTION_NAME = "employee";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    private Role role;

    private Status status;

    @Column(unique = true)
    private String username;

    private String password;

    private String fullName;

    private String avatar;

    @Column(unique = true)
    private String email;

    private String phone;

    private String gender;

    private double balance;

    private String idCardNumber;

    private String city;

    private String district;

    private String address_detail;

    private Date createAt;

    private Date updateAt;


}
