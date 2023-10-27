package com.demo.homemate.dtos.services.response;

import com.demo.homemate.dtos.notification.MessageObject;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceResponse {
    private int serviceId;
    private String name;
    private String img;
    private double price;
    private double discount;
    private String description;
    private MessageObject messageObject;
}
