package com.demo.homemate.dtos.customer.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

//    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    private String dob;

    private boolean isAgreeTermAndService;

//    @SneakyThrows
//    public void setDob(String dob) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        // Tạo đối tượng Date từ chuỗi
//        this.dob = sdf.parse(dob);
//    }

}
