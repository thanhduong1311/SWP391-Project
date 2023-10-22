package com.demo.homemate.mappings;

import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.mappings.interfaces.IEmployeeMapping;
import lombok.SneakyThrows;

public class EmployeMapping implements IEmployeeMapping {



    @SneakyThrows
    @Override
    public EmployeeProlife toEmployeeProfile(Employee employee) {
        try {
            EmployeeProlife prolife = new EmployeeProlife();
            prolife.setName(employee.getFullName());
            prolife.setPhone(employee.getPhone());
            prolife.setEmail(employee.getEmail());
            prolife.setAddress(employee.getAddress_detail());
            prolife.setCity(employee.getCity());
            prolife.setDistrict(employee.getDistrict());
            prolife.setPlaceOfWork(employee.getWork_place());
            prolife.setAvatar(employee.getAvatar());
            prolife.setUsername(employee.getUsername());
            prolife.setIDCard(employee.getIdCardNumber());
            prolife.setBalance(employee.getBalance());

            return prolife;
        } catch (Exception e)  {
            throw new Exception(e.getMessage());
        }

    }
}
