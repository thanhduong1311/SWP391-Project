package com.demo.homemate.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = Feedbacks.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Feedbacks {

    public final static String COLLECTION_NAME = "feedback";

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    private String detail;

    private int point;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @OneToOne
    @JoinColumn(name = "job_id",referencedColumnName = "job_id")
    private Job jobId;

    @OneToMany(mappedBy = "jobId")
    private List<Job> jobs;
}