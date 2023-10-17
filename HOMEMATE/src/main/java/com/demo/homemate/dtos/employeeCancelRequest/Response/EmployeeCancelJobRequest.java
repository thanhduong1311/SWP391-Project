package com.demo.homemate.dtos.employeeCancelRequest.Response;

import com.demo.homemate.enums.RequestStatus;
import com.demo.homemate.enums.Role;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCancelJobRequest {
    private int JobId;
    private int RequestID;
    private int accountId;
    private String account;
    private String accountName;
    private String reason;
    private Role role;
    private RequestStatus status;
    private RequestStatus requestStatus;
}
