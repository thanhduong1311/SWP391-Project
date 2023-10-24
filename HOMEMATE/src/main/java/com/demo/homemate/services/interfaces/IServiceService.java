package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.dtos.services.response.ServiceResponse;

import java.util.List;

public interface IServiceService {

    List<ServiceResponse> getAllServices();

    ServiceResponse getServiceDetail(int id);
    List<ServiceDetailResponse> getAllDetailServices();
    ServiceDetailResponse getServiceByName(String name);
}
