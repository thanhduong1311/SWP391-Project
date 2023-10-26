package com.demo.homemate.dtos.feedback;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FeedbackRequest {
    private int feedbackId;

    private String detail;

    private int point;

    private int customerId;

    private int jobId;


}
