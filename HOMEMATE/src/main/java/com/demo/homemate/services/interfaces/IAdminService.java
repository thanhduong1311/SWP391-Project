package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.homemateService.request.ServiceRequest;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Service;

import java.util.List;

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
     *  handel for add new service
     * @param request
     * @return
     */
    public int handelAddNewService(ServiceRequest request);

    /**
     * handel for update service
     * @param request
     * @return
     */
    public int handelUpdateService(ServiceRequest request);

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

    /**
     * add new service
     * @param request
     * @return
     */
    public ServiceResponse addService(ServiceRequest request);

    /**
     *  update service
     * @param request
     * @return
     */
    public MessageOject updateService(ServiceRequest request);


    /**
     * delete service
     * @param id
     * @return
     */
    public MessageOject deleteService(int id);


    /**
     * block customer account
     * @param id
     * @return
     */
    public MessageOject blockCustomer(int id);

    /**
     * un block customer account
     * @param id
     * @return
     */
    public MessageOject unBlockCustomer(int id);

    /**
     *
     * @param id
     * @return
     */
    public MessageOject deleteCustomer(int id);


    public MessageOject blockEmployee(int id);

    /**
     * un block customer account
     * @param id
     * @return
     */
    public MessageOject unBlockEmployee(int id);

    /**
     * delete employee
     * @param id
     * @return
     */
    public MessageOject deleteEmployee(int id);

    /**
     * approve partner
     * @param id
     * @return
     */
    public MessageOject approvePartner(int id);

    /**
     * reject partner
     * @param id
     * @return
     */
    public MessageOject rejectPartner(int id);





}
