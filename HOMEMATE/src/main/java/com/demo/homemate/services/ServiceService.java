package com.demo.homemate.services;

import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.dtos.services.response.ServiceResponse;
import com.demo.homemate.entities.Service;
import com.demo.homemate.mappings.interfaces.IServiceMapping;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IServiceService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService implements IServiceService {

    private final ServiceRepository serviceRepository;

    private final IServiceMapping serviceMapper;

    @Override
    public List<ServiceResponse> getAllServices() {
        List<Service> services =  serviceRepository.findAll();
        return services
                .stream()
                .map(serviceMapper::toServiceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceResponse getServiceDetail(int id) {
        Service service = serviceRepository.findById(id);
        return serviceMapper.toServiceResponse(service);
    }
    @Override
    public ServiceDetailResponse getServiceByName(String name) {
        Service service = serviceRepository.findByName(name);
        return serviceMapper.toServiceDetailResponse(service);
    }

    @Override
    public List<ServiceDetailResponse> getAllDetailServices() {
        List<Service> services =  serviceRepository.findAll();
        return services
                .stream()
                .map(serviceMapper::toServiceDetailResponse)
                .collect(Collectors.toList());
    }


}
