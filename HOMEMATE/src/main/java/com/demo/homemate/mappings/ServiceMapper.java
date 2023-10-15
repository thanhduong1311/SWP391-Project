package com.demo.homemate.mappings;

import com.demo.homemate.dtos.homemateService.request.ServiceRequest;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;
import com.demo.homemate.entities.Service;
import com.demo.homemate.mappings.interfaces.IServiceMapping;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper implements IServiceMapping {

    @Override
    public ServiceRequest toServiceRequest(Service service) {
        ServiceRequest request = new ServiceRequest();
        request.setServiceId(service.getServiceId());
        request.setName(service.getName());
        request.setImg(service.getImage());
        request.setPrice(service.getPrice());
        request.setDiscount(service.getDiscount());
        request.setDescription(service.getDescription());
        return request;
    }

    @Override
    public ServiceResponse toServiceResponse(Service service) {
        ServiceResponse response = new ServiceResponse();
        response.setServiceId(service.getServiceId());
        response.setName(service.getName());
        response.setImg((service.getImage()));
        response.setPrice(service.getPrice());
        response.setDiscount(service.getDiscount());
        response.setDescription(service.getDescription());
        return response;
    }
}
