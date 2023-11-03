package com.demo.homemate.entities;

import com.demo.homemate.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = EmployeeRequest.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class    EmployeeRequest {

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

    @SneakyThrows
    public static void main(String[] args) {
        EmployeeRequest employeeRequest =  new EmployeeRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date at = sdf.parse("2000-01-01");
//        employeeRequest.setDecisionAt(sdf.parse("2000-01-01"));
//        System.out.println(employeeRequest.getDecisionAt());
//
//        System.out.println("====");
//
//        LocalDateTime current = LocalDateTime.now();
//        Date ht = new Date();
//        Date tmp = new Date(at.getYear(),at.getMonth(), at.getDate());
//        System.out.println("Current: " + ht );
//
//        Date from = new Date(at.getYear(), at.getMonth(), at.getDate(), 10, 00, 00);
//        Date to = new Date(at.getYear(),  at.getMonth(), at.getDate(), 12, 12, 00);
//
//        long time = to.getTime() - from.getTime();
//
//        System.out.println("Thời gian dịch vụ: " + ((double)time / (1000 * 60 * 60)) + " giờ");


        System.out.println("#####################################################");

        String data =
                "timeStart=" +
                        "2023-10-17T23:16" +
                        "," +
                " timeEnd=" +
                        "2023-10-17T23:16";
        String f = "2023-10-17T23:00";
        String t = "2023-10-17T23:00";

        Date fromDate = sdf.parse(f);
        Date toDate = sdf.parse(t);

        System.out.println("f : " + f);
        System.out.println("t : " + t);

        int hourS = Integer.parseInt(f.split("T")[1].split(":")[0]);
        int minuteS = Integer.parseInt(f.split("T")[1].split(":")[1]);

        int hourE = Integer.parseInt(t.split("T")[1].split(":")[0]);
        int minuteE = Integer.parseInt(t.split("T")[1].split(":")[1]);

        Date StartTime = new Date(fromDate.getYear(),fromDate.getMonth(),fromDate.getDate(),hourS,minuteS,0);
        Date EndTime = new Date(toDate.getYear(),toDate.getMonth(),toDate.getDate(),hourE,minuteE,0);

        long servicetime = EndTime.getTime() - StartTime.getTime();

//        System.out.println("Thời gian dịch vụ: " + ((double)servicetime / (1000 * 60 * 60)) + " giờ");
    }

}
