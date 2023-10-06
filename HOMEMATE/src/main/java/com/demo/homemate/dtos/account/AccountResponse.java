package com.demo.homemate.dtos.account;

import enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AccountResponse {

    private String avatar;

    private String username;

    private String phone;

    private String name;

    private String email;


}
