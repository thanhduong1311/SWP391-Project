package com.demo.homemate.dtos.homemateService.response;

import com.demo.homemate.dtos.notification.MessageOject;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceResponse {
    private String name;
    private String img;
    private double price;
    private double discount;
    private String description;
    private MessageOject messageOject;
}
