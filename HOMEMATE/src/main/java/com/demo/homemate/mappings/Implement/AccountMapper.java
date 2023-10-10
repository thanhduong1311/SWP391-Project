package com.demo.homemate.mappings.Implement;

import com.demo.homemate.dtos.account.response.AccountResponse;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.mappings.IAccountMapper;

/**
 * coi ghi chú ở bản thiết ke
 */
public class AccountMapper implements IAccountMapper {

    @Override
    public AccountResponse toEmployeeResponse(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new AccountResponse()
                .setId(employee.getEmployeeId())
                .setRole(employee.getRole())
                .setName(employee.getFullName())
                .setAvatar(employee.getAvatar())
                .setEmail(employee.getEmail())
                .setPhone(employee.getPhone())
                .setUsername(employee.getUsername());
    }

    @Override
    public AccountResponse toCustomerResponse(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new AccountResponse()
                .setId(customer.getCustomerId())
                .setRole(customer.getRole())
                .setName(customer.getFullName())
                .setAvatar(customer.getAvatar())
                .setEmail(customer.getEmail())
                .setPhone(customer.getPhone())
                .setUsername(customer.getUsername());

    }

    @Override
    public AccountResponse toAdminResponse(Admin admin) {
        if (admin == null) {
            return null;
        }

        return new AccountResponse()
                .setRole(admin.getRole())
                .setName("Admin")
                .setUsername(admin.getUsername());

    }
}
