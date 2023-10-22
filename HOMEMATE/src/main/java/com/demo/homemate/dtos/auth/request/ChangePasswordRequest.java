package com.demo.homemate.dtos.auth.request;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
