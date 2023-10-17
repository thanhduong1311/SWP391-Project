package com.demo.homemate.dtos.employee.request;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRegisterRequest {
    private String username;
    private String password;
    private String ConfirmPassword;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String district;
    private String detailAddress;
    private String phone;
    private String dob;
    private int gender;
    private String cardIdNumber;
    private String placeOfWork;
    private int forteService;

}