package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.homemateService.response.ServiceDetailResponse;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;

import java.util.List;

public interface IServiceService {

    List<ServiceResponse> getAllServices();

    ServiceResponse getServiceDetail(int id);

}
