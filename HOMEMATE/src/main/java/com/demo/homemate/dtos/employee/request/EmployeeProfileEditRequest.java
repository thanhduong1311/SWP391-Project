package com.demo.homemate.dtos.employee.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileEditRequest {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String IDCard;
    private String city;
    private String dob;
    private String district;
    private String placeOfWork;
    private String username;
    private String avatar;
    private double balance;
}
