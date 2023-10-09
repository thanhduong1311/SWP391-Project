package com.demo.homemate.services;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Service;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class AdminService implements IAdminService  {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ServiceRepository serviceRepository;


    @Override
    public List<Service> getAllService() {
        List<Service> services = serviceRepository.findAll();
        return services;
    }

    @Override
    public Service getAService(int serviceID) {
        Service service = serviceRepository.findById(serviceID);
        return service;
    }

    @Override
    public int addNewService(Service service) {
        if(serviceRepository.findByName(service.getName().trim()) == null) {
            return 0;
        } else {
            Service newService = new Service();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDatetime = dateTimeFormatter.format(now);
            Date createAt = new Date(formattedDatetime);

            newService.setName(service.getName());
            newService.setImage(service.getImage());
            newService.setPrice(service.getPrice());
            newService.setDescription(service.getDescription());
            newService.setDiscount(service.getDiscount());
            newService.setCreateAt(createAt);
            newService.setUpdateAt(createAt);

            return serviceRepository.findByName(service.getName().trim()) == null ? 0:1;

        }
    }

    @Override
    public int updateService(Service service) {
        return 0;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return null;
    }

    @Override
    public Customer getACustomer(int customerID) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return null;
    }

    @Override
    public Employee getAnEmployee(int employeeID) {
        return null;
    }

    @Override
    public List<Employee> getAllPartner() {
        return null;
    }

    @Override
    public Employee getAPartner(int employeeID) {
        return null;
    }

    @Override
    public int removeAnUser(int userID) {
        return 0;
    }

    @Override
    public int blockAnUser(int userID) {
        return 0;
    }

    @Override
    public int unblockAnUser(int userID) {
        return 0;
    }
}
