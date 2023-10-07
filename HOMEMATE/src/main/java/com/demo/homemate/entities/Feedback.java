package com.demo.homemate.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = Rank.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Feedback {

    public final static String COLLECTION_NAME = "feedback";

    @Id
    @Column(name = "feedback_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    private String detail;

    private int point;

    private int customerId;

    private int jobId;

    private double minSpend;

    private double discount;
}