package com.demo.homemate.dtos.services.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceRequest {
    private int serviceId;
    private String name;
    private String img;
    private double price;
    private double discount;
    private String description;
}
