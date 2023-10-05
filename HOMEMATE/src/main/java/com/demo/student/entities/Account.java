package com.demo.student.entities;

import enums.Role;
import enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;


import java.util.Date;

@Entity
@Table(name = Account.COLLECTION_NAME)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Account {

    public static final String COLLECTION_NAME = "account";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Role role;

    private Status status;

    @Column(unique = true)
    private String username;

    private String password;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String phone;

    private String gender;

    private String address;

    private double balance;

    private Date createAt;

    private Date updateAt;


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", role=" + role +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }
}
