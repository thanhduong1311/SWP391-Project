package com.demo.homemate.entities;

import com.demo.homemate.enums.RequestStatus;
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
public class EmployeeRequest {

    public final static String COLLECTION_NAME = "employeeRequest";

    @Id
    @Column(name = "request_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    private int jobId;

    private int employeeId;

    private String reason;

    private RequestStatus status;

    private Date decisionAt;

    private Date create_at;

    private Date update_at;


}
