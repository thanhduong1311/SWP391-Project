package com.demo.homemate.dtos.customer.response;


import com.demo.homemate.dtos.notification.MessageOject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String username;

    private String phone;

    private String name;

    private String email;

    private int stateCode;

    private MessageOject messageOject;

}
