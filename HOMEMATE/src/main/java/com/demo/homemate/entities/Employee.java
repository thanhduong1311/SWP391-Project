package com.demo.homemate.entities;

import com.demo.homemate.enums.Role;
import com.demo.homemate.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.coyote.Request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = Employee.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Employee{

    public static final String COLLECTION_NAME = "employee";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "employee_id")
    private int employeeId;

    private Role role;

    private AccountStatus accountStatus;

    @Column(unique = true)
    private String username;

    private String password;

    private String fullName;

    private String avatar;

    @Column(unique = true)
    private String email;

    private Date dob;

    private String phone;

    private String gender;

    private double balance;

    private String idCardNumber;

    private String city;

    private String district;

    private String address_detail;

    private String work_place;

    private Date createAt;

    private Date updateAt;

    @OneToMany(mappedBy = "incomeId")
    private List<Income> imcomes;

    @OneToMany(mappedBy = "requestId")
    private List<EmployeeRequest> requests;

    @OneToMany(mappedBy = "jobId")
    private List<Job> jobs;

    @ManyToMany
    @JoinTable(
            name = "major",
            joinColumns = @JoinColumn(
                    name = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId")
    )
    private List<Service> services;

}
