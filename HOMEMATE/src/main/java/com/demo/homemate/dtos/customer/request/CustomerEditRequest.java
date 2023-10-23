package com.demo.homemate.dtos.customer.request;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class CustomerEditRequest {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;
    private Date dob;
    private String district;
    private String username;
    private String avatar;
}
