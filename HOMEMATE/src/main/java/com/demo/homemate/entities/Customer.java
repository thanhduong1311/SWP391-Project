package com.demo.homemate.entities;

import com.demo.homemate.enums.Role;
import com.demo.homemate.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = Customer.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Customer{
    public static final String COLLECTION_NAME = "customer";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    private Role role;

    private AccountStatus accountStatus;

    @Column(unique = true)
    private String username;

    private String password;

    private String fullName;

    private String avatar;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String dob;

    private String gender;

    private String city;

    private double balance;

    private String district;

    private String address_detail;

    private double totalSpend;

    private Date createAt;

    private Date updateAt;

    @OneToMany(mappedBy = "jobId")
    private List<Job> jobs;

    @OneToMany(mappedBy = "feedbackId")
    private List<Feedbacks> feedbacks;

    @OneToMany(mappedBy = "reportId")
    private List<Report> reports;

    @OneToMany(mappedBy = "customer")
    List<Member> members;

}
