package com.demo.homemate.mappings.interfaces;

import com.demo.homemate.dtos.employee.response.EmployeeProlife;
import com.demo.homemate.entities.Employee;

public interface IEmployeeMapping {

    EmployeeProlife toEmployeeProfile(Employee employee);
}
