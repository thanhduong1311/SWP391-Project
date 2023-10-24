package com.demo.homemate.mappings;

import com.demo.homemate.dtos.services.request.ServiceRequest;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.dtos.services.response.ServiceResponse;
import com.demo.homemate.entities.Service;
import com.demo.homemate.mappings.interfaces.IServiceMapping;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.PaymentService;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public ServiceDetailResponse toServiceDetailResponse(Service response) {
        ServiceDetailResponse detailResponse = new ServiceDetailResponse();
        detailResponse.setServiceId(response.getServiceId());
        detailResponse.setName(response.getName());
        detailResponse.setImg(response.getImage());
        detailResponse.setPrice(response.getPrice());
        detailResponse.setDiscount(response.getDiscount());
            String decry = response.getDescription();
            String[] description = decry.split(">>>>>");
            String[] details = description[2].split("###");
        detailResponse.setIntro(description[0]);
        detailResponse.setDescription(description[1]);
        detailResponse.setDetails(details);

        return detailResponse;
    }


}
