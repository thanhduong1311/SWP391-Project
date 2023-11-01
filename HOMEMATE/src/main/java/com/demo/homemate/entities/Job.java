package com.demo.homemate.entities;

import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = Job.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Job {

    public final static String COLLECTION_NAME = "job";

    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    private JobStatus status;

    private String description;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service serviceId;

    private Date start;

    private Date end;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    private PaymentType paymentType;

    private Date createAt;

    private Date updateAt;

    private String location;

    private String jobAddress;

    @OneToOne(mappedBy = "jobId")
    private Income income;

    @OneToOne(mappedBy = "jobId")
    private Feedbacks feedback;

    @OneToOne(mappedBy = "jobId")
    private Report report;

    @OneToOne(mappedBy = "jobId")
    private EmployeeRequest request;

}