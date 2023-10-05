package com.demo.student.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = Customer.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Customer extends BaseAccount{
    public static final String COLLECTION_NAME = "customer";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    private double totalSpend;


}
