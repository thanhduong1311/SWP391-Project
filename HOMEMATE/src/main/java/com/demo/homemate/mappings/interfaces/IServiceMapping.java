package com.demo.homemate.mappings.interfaces;

import com.demo.homemate.dtos.services.request.ServiceRequest;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.dtos.services.response.ServiceResponse;
import com.demo.homemate.entities.Service;

public interface IServiceMapping {
    public ServiceRequest toServiceRequest(Service response);
    public ServiceResponse toServiceResponse(Service request);
public ServiceDetailResponse toServiceDetailResponse(Service response);
}
