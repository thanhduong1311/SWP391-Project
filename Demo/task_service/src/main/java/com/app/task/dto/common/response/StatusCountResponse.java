package com.app.task.dto.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusCountResponse {
    private String status;
    private Integer count;

    public StatusCountResponse(String status, Integer count) {
        this.status = status;
        this.count = count;
    }
}
