package com.demo.homemate.dtos.auth.response;

import com.demo.homemate.dtos.account.AccountResponse;
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
public class AuthenticationResponse {
    private String pageReturn;
    private AccountResponse accountResponse;
    private int stateCode;
    private String token;
}
