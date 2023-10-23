package com.demo.homemate.mappings.interfaces;

import com.demo.homemate.dtos.customer.response.CustomerProfileResponse;
import com.demo.homemate.entities.Customer;

public interface ICustomerMapping {
    CustomerProfileResponse toCustomerProfile (Customer customer);

}
