package com.demo.homemate.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = Service.COLLECTION_NAME)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    public static final String COLLECTION_NAME = "service";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(unique = true,nullable = false)
    private String name;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;

    private Double discount;

    private Date createAt;

    private Date updateAt;

    @OneToMany(mappedBy = "jobId")
    private List<Job> jobs;

    @ManyToMany(mappedBy = "services")
    private List<Employee> employees;

}
