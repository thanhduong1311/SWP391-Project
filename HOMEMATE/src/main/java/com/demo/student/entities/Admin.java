package com.demo.student.entities;


import enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = Admin.COLLECTION_NAME)
@Getter
@Setter
@ToString
public class Admin {

    public static final String COLLECTION_NAME = "admin";

    @Id
    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    private Role role;
}
