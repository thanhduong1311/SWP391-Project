package com.demo.homemate.services;

import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;

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

    /**
     *
     * @param email
     * @return 0 does not exist
     * @return 1 is employee
     * @return 2 is customer
     * @return 3 is admin
     */
    public int checkEmail(String email);

    /**
     * Nhận đối tuong request
     * @param request
     * @return 0 username or password is incorrect
     * @return 1 is employee login success
     * @return 2 is customer login success
     * @return 3 is admin login success
     *
     */
    public int checkLogin(AuthenticationRequest request);

    /**
     * get role
     * @param customer
     * @return
     */
    public int getRole(Customer customer);
    /**
     * get role
     * @param employee
     * @return
     */
    public int getRole(Employee employee);
    /**
     * get role
     * @param admin
     * @return
     */
    public int getRole(Admin admin);


}
