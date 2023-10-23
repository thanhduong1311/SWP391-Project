package com.demo.homemate.mappings;

import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.mappings.interfaces.ICustomerMapping;
import lombok.SneakyThrows;

import java.util.Objects;

public class CustomerMapping implements ICustomerMapping {

    @SneakyThrows
    @Override
    public CustomerProfileRequest toCustomerProfile(Customer customer) {
        try {
            CustomerProfileRequest prolife = new CustomerProfileRequest();
            prolife.setName(customer.getFullName());
            prolife.setPhone(customer.getPhone());
            prolife.setEmail(customer.getEmail());
            prolife.setAddress(customer.getAddress_detail());
            prolife.setCity(customer.getCity());
            prolife.setDistrict(customer.getDistrict());
            prolife.setAvatar(customer.getAvatar());
            prolife.setUsername(customer.getUsername());
            prolife.setBalance(customer.getBalance());
            prolife.setDob(customer.getDob());
            prolife.setTotalSpend(customer.getTotalSpend());
            return prolife;
        } catch (Exception e)  {
            throw new Exception(e.getMessage());
        }
    }
    public Customer toCustomerFromCustomerProfile(Customer c, CustomerProfileRequest cpr){

        if (!Objects.equals(cpr.getName(), c.getFullName())){
            c.setFullName(cpr.getName());
        }
        if (!Objects.equals(cpr.getPhone(), c.getPhone())){
            c.setPhone(cpr.getPhone());
        }
        if (cpr.getDob()!=c.getDob()){
            c.setDob(cpr.getDob());
        }
        if (!Objects.equals(cpr.getAddress(), c.getAddress_detail())){
            c.setAddress_detail(cpr.getAddress());
        }
        if (!Objects.equals(cpr.getCity(), c.getCity())){
            c.setCity(cpr.getCity());
        }
        if (!Objects.equals(cpr.getDistrict(), c.getDistrict())){
            c.setDistrict(cpr.getDistrict());
        }
        if (!Objects.equals(cpr.getAvatar(), c.getAvatar())){
            c.setAvatar(cpr.getAvatar());
        }
        return c;
    }

}
