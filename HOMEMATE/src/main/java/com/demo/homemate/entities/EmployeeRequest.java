package com.demo.homemate.entities;

import com.demo.homemate.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = EmployeeRequest.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeRequest {

    public final static String COLLECTION_NAME = "employeeRequest";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int requestId;

    @OneToOne
    @JoinColumn(name = "job_id",referencedColumnName = "job_id")
    private Job jobId;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    private String reason;

    private RequestStatus status;

    private Date decisionAt;

    private Date createAt;

    private Date updateAt;


}
