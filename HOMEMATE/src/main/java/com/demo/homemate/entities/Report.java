package com.demo.homemate.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = Report.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Report {

    public final static String COLLECTION_NAME = "report";

    @Id
    @Column(name = "reportId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @OneToOne
    @JoinColumn(name = "job_id", referencedColumnName ="job_id" )
    private Job jobId;

    private String reason;

    private Date createAt;

    private Date updateAt;
}