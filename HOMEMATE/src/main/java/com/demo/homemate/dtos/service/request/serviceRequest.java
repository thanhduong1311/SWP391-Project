package com.demo.homemate.dtos.service.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class serviceRequest {
    private int serviceId;

    @Column (unique = true,nullable = false)
    private String name;

    private String image;

    private String description;

    private Double price;

    private Double discount;
}

