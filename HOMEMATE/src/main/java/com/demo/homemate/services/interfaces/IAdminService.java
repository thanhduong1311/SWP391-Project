package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.notification.MessageObject;
import com.demo.homemate.dtos.services.request.ServiceRequest;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;

import java.util.List;

public interface IAdminService {
    /**
     * get all service
     * @return
     */
    public List<ServiceDetailResponse> getAllService();

    /**
     * get a service
     *
     * @param serviceID
     * @return
     */
    public ServiceDetailResponse getAService(int serviceID);

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
    public MessageObject addService(ServiceDetailResponse request, String details);

    /**
     *  update service
     * @param request
     * @return
     */
    public MessageObject updateService(ServiceDetailResponse request, String detail);


    /**
     * delete service
     * @param id
     * @return
     */
    public MessageObject deleteService(int id);


    /**
     * block customer account
     * @param id
     * @return
     */
    public MessageObject blockCustomer(int id);

    /**
     * un block customer account
     * @param id
     * @return
     */
    public MessageObject unBlockCustomer(int id);

    /**
     *
     * @param id
     * @return
     */
    public MessageObject deleteCustomer(int id);


    public MessageObject blockEmployee(int id);

    /**
     * un block customer account
     * @param id
     * @return
     */
    public MessageObject unBlockEmployee(int id);

    /**
     * delete employee
     * @param id
     * @return
     */
    public MessageObject deleteEmployee(int id);

    /**
     * approve partner
     * @param id
     * @return
     */
    public MessageObject approvePartner(int id);

    /**
     * reject partner
     * @param id
     * @return
     */
    public MessageObject rejectPartner(int id);





}
