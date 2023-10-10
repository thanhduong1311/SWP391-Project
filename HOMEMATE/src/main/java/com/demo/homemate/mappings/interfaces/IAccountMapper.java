package com.demo.homemate.mappings.interfaces;

import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;

public interface IAccountMapper {
    /**
     *
     * trả về thông tin emloyee hien thi ra giao diện
     * @param employee
     * @return
     */
    AccountResponse toEmployeeResponse(Employee employee);

    /**
     * trả về thông tin customer hien thi ra giao diện
     * @param employee
     * @return
     */
    AccountResponse toCustomerResponse(Customer employee);

    /**
     * trả về thông tin admin hien thi ra giao diện
     * @param employee
     * @return
     */
    AccountResponse toAdminResponse(Admin employee);

}
