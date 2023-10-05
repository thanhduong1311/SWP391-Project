package com.demo.student.entities;

import enums.Role;
import enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;


import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@ToString
public class BaseAccount {


    private Role role;

    private Status status;

//    @Column(unique = true)
    private String username;

    private String password;

    private String fullName;

//    @Column(unique = true)
    private String email;

    private String phone;

    private String gender;

    private String address;

    private double balance;

    private Date createAt;

    private Date updateAt;

}
