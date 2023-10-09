package com.demo.homemate.services.interfaces;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Service;

import java.util.List;
import java.util.Optional;

public interface IAdminService {
    /**
     * get all service
     * @return
     */
    public List<Service> getAllService();

    /**
     * get a service
     *
     * @param serviceID
     * @return
     */
    public Service getAService(int serviceID);

    /**
     * add new service
     * @param service
     * @return
     */
    public int addNewService(Service service);

    /**
     * update service
     * @param service
     * @return
     */
    public int updateService(Service service);

    /**
     * get all customer
     * @return
     */
    public List<Customer> getAllCustomer();


    /**
     * get customer
     * @param customerID
     * @return
     */
    public Customer getACustomer(int customerID);

    /**
     * get all employee
     * @return
     */
    public List<Employee> getAllEmployee();

    /**
     * get employee
     * @param employeeID
     * @return
     */
    public Employee getAnEmployee(int employeeID);

    /**
     * get all partner register (employee with status account = 4)
     * @return
     */
    public List<Employee> getAllPartner();

    /**
     * get a partner register
     * @param employeeID
     * @return
     */
    public Employee getAPartner(int employeeID);

    /**
     * remove account
     * @param userID
     * @return
     */
    public int removeAnUser(int userID);

    /**
     * block account
     * @param userID
     * @return
     */
    public int blockAnUser(int userID);

    /**
     * unblock account
     * @param userID
     * @return
     */
    public int unblockAnUser(int userID);



}
