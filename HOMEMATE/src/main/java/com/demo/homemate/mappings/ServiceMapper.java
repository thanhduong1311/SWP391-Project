package com.demo.homemate.mappings;

import com.demo.homemate.dtos.homemateService.request.ServiceRequest;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;
import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.homemate.dtos.payment.PaymentRequest;
import com.demo.homemate.entities.Service;
import com.demo.homemate.mappings.interfaces.IServiceMapping;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.PaymentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@org.springframework.stereotype.Service
@Component
public class ServiceMapper implements IServiceMapping {

    private  PaymentService paymentService;

    private  ServiceRepository serviceRepository;

    public ServiceMapper() {
    }

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
