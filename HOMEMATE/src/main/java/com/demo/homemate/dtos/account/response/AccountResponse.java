package com.demo.homemate.dtos.account.response;

import com.demo.homemate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AccountResponse {

    private int id;

    private Role role;

    private String avatar;

    private String username;

    private String phone;

    private String name;

    private String email;


}
