package com.demo.homemate.mappings.interfaces;

import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.entities.Customer;

public interface ICustomerMapping {
    CustomerProfileRequest toCustomerProfile (Customer customer);

}
