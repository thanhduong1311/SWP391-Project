package com.demo.homemate.dtos.services.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceDetailResponse {
    private int serviceId;
    private String name;
    private String img;
    private double price;
    private double discount;
    private String intro;
    private String[] details;
    private String description;
}
