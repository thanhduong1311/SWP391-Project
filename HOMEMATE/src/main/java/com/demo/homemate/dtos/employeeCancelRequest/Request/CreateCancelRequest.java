package com.demo.homemate.dtos.employeeCancelRequest.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CreateCancelRequest {
    private int jobId;
    private String reason;
}
