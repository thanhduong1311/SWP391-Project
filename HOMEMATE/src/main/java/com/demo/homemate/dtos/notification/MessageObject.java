package com.demo.homemate.dtos.notification;


import com.demo.homemate.dtos.email.EmailDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageObject {
    private String name;
    private String message;
    private EmailDetails emailMessage;
}
