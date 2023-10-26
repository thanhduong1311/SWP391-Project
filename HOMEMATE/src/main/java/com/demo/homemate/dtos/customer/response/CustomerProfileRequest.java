package com.demo.homemate.dtos.customer.response;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileRequest {
        private String name;
        private String phone;
        private String email;
        private String address;
        private double totalSpend;
        private String city;
        private String dob;
        private String district;
        private String username;
        private String avatar;
        private double balance;
    }

