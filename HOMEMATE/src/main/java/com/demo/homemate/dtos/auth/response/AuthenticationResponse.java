package com.demo.homemate.dtos.auth.response;

import com.demo.homemate.dtos.account.response.AccountResponse;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class AuthenticationResponse {
    private String pageReturn;
    private AccountResponse accountResponse;
    private int stateCode;
    private String token;



    public static void main(String[] args) {

        AuthenticationResponse response1 = new AuthenticationResponse();
        response1.setToken("av");
        response1.setStateCode(123);
    }
}
