package com.demo.homemate.entities;

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
public class Report {

    public final static String COLLECTION_NAME = "report";

    @Id
    @Column(name = "report_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    private int customerId;

    private int jobId;

    private String reason;

    private Date create_at;

    private Date update_at;
}