package com.demo.homemate.entities;


import com.demo.homemate.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = Admin.COLLECTION_NAME)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    public static final String COLLECTION_NAME = "admin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    private Role role;

}
