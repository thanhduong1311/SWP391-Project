package com.demo.homemate.dtos.employee.response;

import com.demo.homemate.dtos.notification.MessageObject;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerResponse {
    private String username;

    private String phone;

    private String name;

    private String email;

    private int stateCode;

    private MessageObject messageObject;

}