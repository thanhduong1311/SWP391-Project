package com.demo.homemate.services;

import com.demo.homemate.dtos.customerReport.responese.CustomerReportJob;
import com.demo.homemate.dtos.employeeRequest.Response.EmployeeCancelJobRequest;
import com.demo.homemate.dtos.homemateService.request.ServiceRequest;
import com.demo.homemate.dtos.homemateService.response.ServiceResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.*;
import com.demo.homemate.enums.AccountStatus;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.*;
import com.demo.homemate.services.interfaces.IAdminService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AdminService implements IAdminService  {

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;





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
    public int handelAddNewService(ServiceRequest request) {
        Service service = new Service();
        service.setName(request.getName());
        service.setPrice(request.getPrice());
        service.setImage(request.getImg());
        service.setDiscount(request.getDiscount());
        service.setDescription(request.getDescription());

        if(serviceRepository.findByName(service.getName().trim()) != null) {
            return 0;
        } else {
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDatetime = dateTimeFormatter.format(now);
//            Date createAt = new Date(formattedDatetime);

            service.setName(request.getName());
            service.setPrice(request.getPrice());
            service.setImage(request.getImg());
            service.setDiscount(request.getDiscount());
            service.setDescription(request.getDescription());
//            service.setCreateAt(createAt);
//            service.setUpdateAt(createAt);

            serviceRepository.save(service);

            return serviceRepository.findByName(service.getName().trim()) == null ? 0:1;

        }
    }

    @Override
    public int handelUpdateService(ServiceRequest request) {

        Service service = serviceRepository.findById(request.getServiceId());


        if (serviceRepository.findByName(service.getName().trim()) != null ||
                serviceRepository.findById(request.getServiceId()) == null) {
            return 0;
        } else {
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDatetime = dateTimeFormatter.format(now);
//            Date createAt = new Date(formattedDatetime);

            service.setName(request.getName());
            service.setPrice(request.getPrice());
            service.setImage(request.getImg());
            service.setDiscount(request.getDiscount());
            service.setDescription(request.getDescription());

//            service.setCreateAt(createAt);
//            service.setUpdateAt(createAt);

            serviceRepository.save(service);

            return serviceRepository.findByName(service.getName().trim()) == null  ? 0 : 1;

        }
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customers =  customerRepository.findAll();
        return customers;
    }

    @Override
    public Customer getACustomer(int customerID) {
        Customer customer = customerRepository.findById(customerID);
        return customer;
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employees =  employeeRepository.findAll();
        List<Employee> result = new ArrayList<>();
        for (Employee e: employees
             ) {
            if(e.getRole().ordinal() == 2 && e.getAccountStatus().ordinal()!=2) result.add(e);
        }
        return result;
    }

    @Override
    public Employee getAnEmployee(int employeeID) {
        Employee employee = employeeRepository.findById(employeeID);
        return employee;
    }

    @Override
    public List<Employee> getAllPartner() {
        List<Employee> employees =  employeeRepository.findAll();
        List<Employee> result = new ArrayList<>();
        for (Employee e: employees
        ) {
            if(e.getAccountStatus().ordinal() == 2 && e.getRole().ordinal() == 2) result.add(e);
        }
        return result;
    }

    @Override
    public Employee getAPartner(int partnerID) {
        Employee partner =  employeeRepository.findById(partnerID);
        return partner;
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

    @Override
    public ServiceResponse addService(ServiceRequest request) {
        ServiceResponse response = new ServiceResponse();
        if (handelAddNewService(request) == 1) {
            response.setName(request.getName());
            response.setImg(request.getImg());
            response.setPrice(request.getPrice());
            response.setDiscount(request.getDiscount());
            response.setDescription(request.getDescription());
            response.setMessageOject(new MessageOject("Success", "Add service successfully!",null));
            return response;
        }
        response.setMessageOject(new MessageOject("Failed", "Add service failed!",null));
        return response;
    }

    @Override
    public MessageOject updateService(ServiceRequest request) {
        Service service = serviceRepository.findById(request.getServiceId());
        if(service != null) {
            service.setName(request.getName());
            service.setImage(request.getImg());
            service.setPrice(request.getPrice());
            service.setDiscount(request.getDiscount());
            service.setDescription(request.getDescription());
            serviceRepository.save(service);
            return new MessageOject("Succes","Update service successfully!",null);
        }
        return new MessageOject("Failed","Update service Failed!",null);

    }

    @Override
    public MessageOject deleteService(int id) {
        ServiceResponse response= new ServiceResponse();
        Service service = serviceRepository.findById(id);

        if (service == null) {
           return new MessageOject("Failed","Can not find service with ID = " + id,null);
        } else {
            serviceRepository.delete(service);
            service = serviceRepository.findById(id);
            if (service != null) {
               return  (new MessageOject("Failed","Can not delete service with ID = " + id,null));
            } else {
                return (new MessageOject("Success","Service delete successfully!",null));
            }
        }
    }

    @Override
    public MessageOject blockCustomer(int id) {
        Customer customer = customerRepository.findById(id);

        if (customer == null) {
            return new MessageOject("Failed","Can not block this account!",null );
        }  else {
            customer.setAccountStatus(AccountStatus.BLOCKED);
            customerRepository.save(customer);
            customer = customerRepository.findById(id);
            if(customer.getAccountStatus().ordinal() == 1) {
                return new MessageOject("Success","Account is blocked",null );
            }
            return new MessageOject("Failed","Can not block this account!",null );
        }
    }

    @Override
    public MessageOject unBlockCustomer(int id) {
        Customer customer = customerRepository.findById(id);

        if (customer == null) {
            return new MessageOject("Failed","Can not unblock this account!",null );
        }  else {
            customer.setAccountStatus(AccountStatus.ACTIVE);
            customerRepository.save(customer);
            customer = customerRepository.findById(id);
            if(customer.getAccountStatus().ordinal() == 0) {
                return new MessageOject("Success","Account is unblocked",null );
            } else {
                return new MessageOject("Failed","Can not unblock this account!",null );
            }
        }
    }

    @Override
    public MessageOject deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id);

        if (customer == null) {
            return new MessageOject("Failed","Can not delete this account!",null );
        }  else {
            customerRepository.delete(customer);
            customer = customerRepository.findById(id);
            if(customer == null) {
                return new MessageOject("Success","Account is deleted",null );
            } else {
                return new MessageOject("Failed","Can not delete this account!",null );
            }
        }
    }

    @Override
    public MessageOject blockEmployee(int id) {
        Employee employee = employeeRepository.findById(id);

        if (employee == null) {
            return new MessageOject("Failed","Can not block this account!",null );
        }  else {
            employee.setAccountStatus(AccountStatus.BLOCKED);
            employeeRepository.save(employee);
            employee = employeeRepository.findById(id);
            if(employee.getAccountStatus().ordinal() == 1) {
                return new MessageOject("Success","Account is blocked",null );
            }
            return new MessageOject("Failed","Can not block this account!",null );
        }
    }

    @Override
    public MessageOject unBlockEmployee(int id) {
        Employee employee = employeeRepository.findById(id);

        if (employee == null) {
            return new MessageOject("Failed","Can not unblock this account!",null );
        }  else {
            employee.setAccountStatus(AccountStatus.ACTIVE);
            employeeRepository.save(employee);
            employee= employeeRepository.findById(id);
            if(employee.getAccountStatus().ordinal() == 0) {
                return new MessageOject("Success","Account is unblocked",null );
            } else {
                return new MessageOject("Failed","Can not unblock this account!",null );
            }
        }
    }

    @Override
    public MessageOject deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id);

        if (employee == null) {
            return new MessageOject("Failed","Can not delete this account!",null );
        }  else {
            employeeRepository.delete(employee);
            employee = employeeRepository.findById(id);
            if(employee == null) {
                return new MessageOject("Success","Account is deleted",null );
            } else {
                return new MessageOject("Failed","Can not delete this account!",null );
            }
        }
    }

    @Override
    public MessageOject approvePartner(int id) {
        Employee employee = employeeRepository.findById(id);

        if (employee == null) {
            return new MessageOject("Failed","Can not approve this partner!",null );
        }  else {
            employee.setAccountStatus(AccountStatus.ACTIVE);
            employee.setRole(Role.EMPLOYEE);
            employeeRepository.save(employee);
            employee= employeeRepository.findById(id);
            if(employee.getAccountStatus().ordinal() == 0 && employee.getRole().ordinal() == 2) {
                return new MessageOject("Success","Partner is approved",null );
            } else {
                return new MessageOject("Failed","Can not approve this partner!",null );
            }
        }
    }

    @Override
    public MessageOject rejectPartner(int id) {
        return null;
    }






}


