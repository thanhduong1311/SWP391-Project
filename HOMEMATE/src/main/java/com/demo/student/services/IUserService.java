package com.demo.student.services;

import com.demo.student.dtos.auth.request.AuthenticationRequest;
import com.demo.student.entities.Admin;
import com.demo.student.entities.Customer;
import com.demo.student.entities.Employee;

public interface IUserService {

    /**
     *
     * Check exist of username
     * @param username
     * @return 0 does not exist
     * @return 1 is employee
     * @return 2 is customer
     * @return 3 is admin
     */
    public int checkUsername(String username);

    public int checkEmail(String email);

    public int checkLogin(AuthenticationRequest request);

    public int getRole(Customer customer);
    public int getRole(Employee customer);
    public int getRole(Admin admin);


}
