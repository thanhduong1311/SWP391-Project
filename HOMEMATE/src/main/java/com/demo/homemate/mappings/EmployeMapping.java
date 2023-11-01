package com.demo.homemate.mappings;

import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.mappings.interfaces.IEmployeeMapping;
import com.demo.homemate.utils.JobTimer;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeMapping implements IEmployeeMapping {



    @SneakyThrows
    @Override
    public EmployeeProlife toEmployeeProfile(Employee employee) {
        try {
            EmployeeProlife prolife = new EmployeeProlife();
            JobTimer jobTimer = new JobTimer();
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
            prolife.setDob(jobTimer.toBirthDay(employee.getDob()));

            return prolife;
        } catch (Exception e)  {
            throw new Exception(e.getMessage());
        }

    }
    public Employee toEmployeeFromEmployeeProfile(Employee employee,EmployeeProlife request) throws ParseException {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            employee.setDob(sdf.parse(request.getDob()));
            employee.setFullName(request.getName());
            employee.setPhone(request.getPhone());
            employee.setAddress_detail(request.getAddress());
            employee.setCity(request.getCity());
            employee.setDistrict(request.getDistrict());
            employee.setIdCardNumber(request.getIDCard());
            employee.setWork_place(request.getPlaceOfWork());
            employee.setUpdateAt(new Date());

            return employee;
    }
}
