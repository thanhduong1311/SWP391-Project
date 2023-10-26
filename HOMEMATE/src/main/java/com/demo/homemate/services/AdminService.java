package com.demo.homemate.services;

import com.demo.homemate.dtos.services.request.ServiceRequest;
import com.demo.homemate.dtos.services.response.ServiceDetailResponse;
import com.demo.homemate.dtos.services.response.ServiceResponse;
import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.*;
import com.demo.homemate.enums.AccountStatus;
import com.demo.homemate.enums.Role;
import com.demo.homemate.mappings.ServiceMapper;
import com.demo.homemate.repositories.*;
import com.demo.homemate.services.interfaces.IAdminService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AdminService implements IAdminService  {

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;

    private final ServiceService serviceService;

    ServiceMapper serviceMapper = new ServiceMapper();


    @Override
    public List<ServiceDetailResponse> getAllService() {
        return serviceService.getAllDetailServices();
    }

    @Override
    public ServiceDetailResponse getAService(int serviceID) {
        ServiceDetailResponse service = serviceMapper.toServiceDetailResponse(serviceRepository.findById(serviceID));
        return service;
    }

    @Override
    public int handelAddNewService(ServiceRequest request) {
        try {
            Service service = new Service();
            service.setName(request.getName());
            service.setPrice(request.getPrice());
            service.setImage(request.getImg());
            service.setDiscount(request.getDiscount());
            service.setDescription(request.getDescription());


            if(serviceRepository.findByName(service.getName().trim()) != null) {
                return 0;
            } else {
                service.setName(request.getName());
                service.setPrice(request.getPrice());
                service.setImage(request.getImg());
                service.setDiscount(request.getDiscount());
                service.setDescription(request.getDescription());
                service.setCreateAt(new Date());
                service.setUpdateAt(new Date());

                serviceRepository.save(service);

                return serviceRepository.findByName(service.getName().trim()) == null ? 0:1;
            }

        } catch (Exception e) {
            return 0;
        }


    }

    @Override
    public int handelUpdateService(ServiceRequest request) {

        try {
            Service service = serviceRepository.findById(request.getServiceId());

            if (serviceRepository.findByName(service.getName().trim()) != null ||
                    serviceRepository.findById(request.getServiceId()) == null) {
                return 0;
            } else {

                service.setName(request.getName());
                service.setPrice(request.getPrice());
                service.setImage(request.getImg());
                service.setDiscount(request.getDiscount());
                service.setDescription(request.getDescription());

                service.setUpdateAt(new Date());


                serviceRepository.save(service);

                return serviceRepository.findByName(service.getName().trim()) == null  ? 0 : 1;
            }
        } catch (Exception e) {
            return 0;
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
        try {
            List<Employee> employees =  employeeRepository.findAll();
            List<Employee> result = new ArrayList<>();
            for (Employee e: employees
            ) {
                if(e.getRole().ordinal() == 2 && e.getAccountStatus().ordinal()!=2) result.add(e);
            }
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Employee getAnEmployee(int employeeID) {
        Employee employee = employeeRepository.findById(employeeID);
        return employee;
    }

    @Override
    public List<Employee> getAllPartner() {
        try {
            List<Employee> employees =  employeeRepository.findAll();
            List<Employee> result = new ArrayList<>();
            for (Employee e: employees
            ) {
                if(e.getAccountStatus().ordinal() == 2 && e.getRole().ordinal() == 2) result.add(e);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
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
    public MessageOject addService(ServiceDetailResponse response,String detail) {
        ServiceRequest request = new ServiceRequest();
        try {
            request.setServiceId(response.getServiceId());
            request.setImg(response.getImg());
            request.setName(response.getName());
            request.setPrice(response.getPrice());
            request.setDiscount(response.getDiscount());
        String [] listDetail = detail.split("\n");
            String joinedString = String.join("###", listDetail);
            String responseDiscription = response.getIntro()+">>>>>"
                    + response.getDescription()+">>>>>"
                    + joinedString;
            request.setDescription(responseDiscription);


            if (handelAddNewService(request) == 1) {
                return new MessageOject("Success", "Add service successfully!",null);
            }
            return new MessageOject("Failed", "Add service failed!",null);
        } catch (Exception e) {
            return new MessageOject("Error", "Add service error!",null);
        }

    }

    @Override
    public MessageOject updateService(ServiceDetailResponse request,String detail) {
        try {
            Service service = serviceRepository.findById(request.getServiceId());
            if(service != null) {
                service.setName(request.getName());
                service.setImage(request.getImg());
                service.setPrice(request.getPrice());
                service.setDiscount(request.getDiscount());
                String [] listDetail = detail.split("\n");
                String joinedString = String.join("###", listDetail);
                String responseDiscription = request.getIntro()+">>>>>"
                        + request.getDescription()+">>>>>"
                        + joinedString;
                service.setDescription(responseDiscription);
                serviceRepository.save(service);
                return new MessageOject("Success","Update service successfully!",null);
            }
            return new MessageOject("Failed","Update service Failed!",null);
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null);
        }
    }

    @Override
    public MessageOject deleteService(int id) {
        try {
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
        } catch (Exception e) {
            return (new MessageOject("Success",e.getMessage(),null));
        }

    }

    @Override
    public MessageOject blockCustomer(int id) {
        try {
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
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null );
        }

    }

    @Override
    public MessageOject unBlockCustomer(int id) {
        try {
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
    } catch (Exception e) {
        return new MessageOject("Failed",e.getMessage(),null );
    }
    }

    @Override
    public MessageOject deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id);

        try {
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
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null );
        }


    }

    @Override
    public MessageOject blockEmployee(int id) {
        try {
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
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null );
        }
    }

    @Override
    public MessageOject unBlockEmployee(int id) {
        try {
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
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null );
        }
    }

    @Override
    public MessageOject deleteEmployee(int id) {
        try {
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
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null );
        }
    }

    @Override
    public MessageOject approvePartner(int id) {
        try {
        Employee employee = employeeRepository.findById(id);

        if (employee == null) {
            return new MessageOject("Failed","Can not approve this partner!",null );
        }  else {
            employee.setAccountStatus(AccountStatus.ACTIVE);
            employee.setRole(Role.EMPLOYEE);
            employeeRepository.save(employee);
            employee= employeeRepository.findById(id);
            employee.setUpdateAt(new Date());




            if(employee.getAccountStatus().ordinal() == 0 && employee.getRole().ordinal() == 2) {
                return new MessageOject("Success","Partner is approved",null );
            } else {
                return new MessageOject("Failed","Can not approve this partner!",null );
            }
        }
        } catch (Exception e) {
            return new MessageOject("Failed",e.getMessage(),null );
        }
    }

    @Override
    public MessageOject rejectPartner(int id) {
        deleteEmployee(id);
        return new MessageOject("Succes","Partner is rejected",null);
    }

}


