package com.demo.homemate.dtos.error;


import enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOject {
    private String name;
    private String message;
    private Role role;
}
