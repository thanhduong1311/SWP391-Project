package com.demo.homemate.entities;

import com.demo.homemate.enums.JobStatus;
import com.demo.homemate.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = Rank.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job {

    public final static String COLLECTION_NAME = "rank";

    @Id
    @Column(name = "job_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    private JobStatus status;

    private String description;

    private int serviceId;

    private Date from;

    private Date to;

    private int customerId;

    private int employeeId;

    private PaymentType paymentType;

    private Date createAt;

    private Date updateAt;
}