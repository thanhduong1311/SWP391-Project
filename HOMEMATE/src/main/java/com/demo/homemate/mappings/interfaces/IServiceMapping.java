package com.demo.homemate.mappings.interfaces;

import com.demo.homemate.dtos.homemateService.request.ServiceRequest;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;
import com.demo.homemate.entities.Service;

public interface IServiceMapping {
    public ServiceRequest toServiceRequest(Service response);
    public ServiceResponse toServiceResponse(Service request);


}
