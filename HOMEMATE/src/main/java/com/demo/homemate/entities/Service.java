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
public class Service{
    public static final String COLLECTION_NAME = "service";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(unique = true,nullable = false)
    private String name;

    private String image;

    private Double price;

    private Double discount;

    private Date createAt;

    private Date updateAt;



}
