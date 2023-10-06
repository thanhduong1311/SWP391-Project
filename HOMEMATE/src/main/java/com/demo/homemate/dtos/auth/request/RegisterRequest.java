package com.demo.homemate.dtos.auth.request;

import jakarta.validation.constraints.NotBlank;
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
public class RegisterRequest {

    private String firstName;

    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
