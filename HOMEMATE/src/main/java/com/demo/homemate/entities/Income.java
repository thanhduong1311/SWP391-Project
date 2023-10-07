package com.demo.homemate.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = Customer.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Income {
    public static final String COLLECTION_NAME = "imcome";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private int incomeId;

    private int employeeId;

    private int jobId;

    private Double amount;

    private String note;

    private Double discount;

    private Date createAt;

    private Date updateAt;

}
