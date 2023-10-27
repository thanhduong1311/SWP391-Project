package com.demo.homemate.services;

import com.demo.homemate.configurations.JWTService;
import com.demo.homemate.data.MailContents;
import com.demo.homemate.dtos.auth.request.AuthenticationRequest;
import com.demo.homemate.dtos.customer.request.RegisterRequest;
import com.demo.homemate.dtos.employee.request.PartnerRegisterRequest;
import com.demo.homemate.dtos.email.EmailDetails;
import com.demo.homemate.dtos.password.RecoverPassword;
import com.demo.homemate.dtos.password.tokenEmailConfirm;
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.entities.Service;
import com.demo.homemate.enums.AccountStatus;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IUserService;
import com.demo.homemate.utils.JobTimer;
import com.demo.homemate.utils.PasswordMD5;
import com.demo.homemate.utils.PasswordValidate;
import com.demo.homemate.utils.PhoneValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    private final ServiceRepository serviceRepository;



    @Override
    public int checkUsername(String username) {
        Employee e = employeeRepository.findByUsername(username);
        Customer c = customerRepository.findByUsername(username);
        Admin a = adminRepository.findByUsername(username);

        if (e == null && a == null && c == null) {
            return 0;
        } else {
            //Employee
            if (c == null && a == null) {
                return 1;
            }
            //Customer
            if (e == null && a == null) {
                return 2;
            }
            //Admin
            if (e == null && c == null) {
                return 3;
            }
        }
        return 0;
    }

    @Override
    public int checkEmail(String email) {
        Employee e = employeeRepository.findByEmail(email);
        Customer c = customerRepository.findByEmail(email);

        if (e == null && c == null) {
            return 0;
        } else {
            if (e == null) {
                return 1;
            }
            if (c == null) {
                return 2;
            }
        }
        return -1;
    }

    @Override
    public int checkLogin(AuthenticationRequest request) {

        int var = checkUsername(request.getUsername());

        if (var == 0) {
            return 0;
        } else {
            PasswordMD5 p5 = new PasswordMD5();
            String password = "";
            try {
                password =p5.encode(request.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            if (var == 1) {
                Employee e = employeeRepository.findByUsername(request.getUsername());
                if (e.getUsername().equals(request.getUsername()) &&
                        e.getPassword().equals(password)
                ) {
                    return 1;
                } else return 0;

            } else if (var == 2) {
                Customer c = customerRepository.findByUsername(request.getUsername());
                if (c.getUsername().equals(request.getUsername()) &&
                        c.getPassword().equals(password)
                ) {
                    return 2;
                } else return 0;
            } else {
                Admin a = adminRepository.findByUsername(request.getUsername());
                if (a.getUsername().equals(request.getUsername()) &&
                        a.getPassword().equals(password)
                ) {
                    return 3;
                } else return 0;
            }
        }
    }

    @Override
    public int getRole(Customer customer) {
        return customer.getRole().ordinal();
    }

    @Override
    public int getRole(Employee employee) {
        return employee.getRole().ordinal();
    }

    @Override
    public int getRole(Admin admin) {
        return admin.getRole().ordinal();
    }

    @Override
    public int checkPhone(String phone) {
        return PhoneValidator.isValid(phone) == true ? 1:0;
    }

    @Override
    public int checkNewPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword) ? 1:0;
    }

    @Override
    public int checkIDCard(String IDcard) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee e :
             employees) {
            if(e.getIdCardNumber().equals(IDcard.trim())) return 0;
        }
        return 1;
    }

    @SneakyThrows
    @Override
    public int createCustomer(RegisterRequest request) {

        JobTimer jobTimer = new JobTimer();

        int checkPhone = checkPhone(request.getPhone());
        int checkEmail = checkEmail(request.getEmail());
        int checkNewPass = checkNewPassword(request.getPassword(), request.getConfirmPassword());
        int checkUsername = checkUsername(request.getUsername());
        int checkAge = jobTimer.checkAge(request.getDob());


        if(checkPhone != 0 && checkEmail ==0 && checkNewPass !=0 && checkUsername ==0) {
            Customer customer = new Customer();
            customer.setUsername(request.getUsername());
            customer.setPassword(PasswordMD5.encode(request.getPassword()));
            customer.setFullName(request.getLastName() + " " +request.getFirstName());
            customer.setPhone(request.getPhone());
            customer.setEmail(request.getEmail());
            customer.setRole(Role.CUSTOMER);
            customer.setAvatar("/assets/images/defaultUser.png");
            customer.setAccountStatus(AccountStatus.ACTIVE);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            customer.setDob(sdf.parse(request.getDob()));


            customer.setCreateAt(new Date());
            customer.setUpdateAt(new Date());

            customerRepository.save(customer);

         return  customerRepository.findByUsername(request.getUsername()) != null ? 1:0;
        } else {
            if (checkUsername != 0) return 2;
            if (checkNewPass ==0 ) return 5;
            if  (checkPhone == 0) return 4;
            if (checkEmail != 0) return 3;
//            if(checkAge != 0) return 6;
        }
            return 0;
    }


    @SneakyThrows
    @Override
    public int createEmployee(PartnerRegisterRequest request) {

        int checkPhone = checkPhone(request.getPhone());
        int checkEmail = checkEmail(request.getEmail());
        int checkNewPass = checkNewPassword(request.getPassword(), request.getConfirmPassword());
        int checkUsername = checkUsername(request.getUsername());
        int checkIDCard = checkIDCard(request.getCardIdNumber());


        if(checkPhone != 0 && checkEmail ==0 && checkNewPass !=0 && checkUsername ==0 && checkIDCard!=0) {
            Employee employee = new Employee();
            employee.setUsername(request.getUsername());//
            employee.setPassword(PasswordMD5.encode(request.getPassword()));//
            employee.setFullName(request.getLastName() + " " +request.getFirstName());//
            employee.setPhone(request.getPhone());//
            employee.setEmail(request.getEmail());//
            employee.setCity(request.getCity());
            employee.setDistrict(request.getDistrict());
            employee.setAddress_detail(request.getDetailAddress());
            employee.setIdCardNumber(request.getCardIdNumber());
            employee.setWork_place(request.getPlaceOfWork());
            employee.setGender(request.getGender() == 0 ? "male" : "female");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            employee.setDob(sdf.parse(request.getDob()));

            employee.setRole(Role.EMPLOYEE);
            employee.setAvatar("/assets/images/defaultUser.png");
            employee.setAccountStatus(AccountStatus.WAIT_FOR_APPROVE);


           employee.setCreateAt(new Date());
           employee.setUpdateAt(new Date());

            List<Service> s = new ArrayList<>();
            s.add(serviceRepository.findById(request.getForteService()));
            employee.setServices(s);
            employeeRepository.save(employee);



            return employeeRepository.findByUsername(request.getUsername()) != null ? 1 : 0;

        } else {
            if (checkUsername != 0) return 2;
            if (checkNewPass ==0 ) return 3;
            if  (checkPhone == 0) return 4;
            if (checkEmail != 0) return 5;
            if (checkIDCard == 0) return 6;
        }
        return 0;
    }

    public boolean checkNull(String check) {
        return check.equals("") ? true:false;
    }

    /**
     * @param email
     */
    @Override
    public RecoverPassword createCodeRecover(String email) {

        RecoverPassword recoverPassword = new RecoverPassword();
        //check email
        int emailOf = checkEmail(email);
        String fname = "";
        if (emailOf==1){
            fname = customerRepository.findByEmail(email).getFullName();
        }else
            fname = employeeRepository.findByEmail(email).getFullName();

        //Generate random code
        Random rd = new Random();
        int rand = rd.nextInt(999999-100000+1)+100000;

        //Prepare for mail content
        MailContents mailContents = new MailContents();
        mailContents.setSubjectName(fname);
        mailContents.setTitle("[HOMEMATE] RECOVER PASSWORD MAIL");
        mailContents.setCodeRecover(String.valueOf(rand));

        //Set email required information
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(email);
        emailDetails.setSubject(mailContents.getTitle());
        emailDetails.setMsgBody(mailContents.CreateRecoverPassword());


        recoverPassword.setEmail(email);

        //Generate token
        String jwtToken = JWTService.generateJwtRecoverCode(new tokenEmailConfirm().setCode(String.valueOf(rand)).setEmail(email));
        recoverPassword.setExpiration(120);
        recoverPassword.setToken(jwtToken);
        recoverPassword.setEmailDetails(emailDetails);
        //add to respone
        return recoverPassword;
    }

    @Override
    public int checkNewChangePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        int checkU = checkUsername(username);

        if(checkU != 0) {
            boolean checkNull  = checkNull(oldPassword);
            if(!checkNull) {
                int checkO  =  checkOldPassword(username,oldPassword);
                if (checkO !=0) {
                    PasswordValidate pv = new PasswordValidate();
                    boolean checkV =  pv.checkPasswordValidate(newPassword);
                    if (checkV) {
                        int checkN = checkNewPassword(newPassword,confirmPassword);
                        if(checkN != 0) {
                            return 5; // ok
                        } else {
                            return 4;// confirmPassword is wrong
                        }
                    } else {
                        return 3; // new pass is not valid
                    }
                } else {
                    return 2; // old pass is false
                }
            } else {
                return  1; // password is null
            }
        } else {
            return 0; // username not exist
        }
    }

    @Override
    public int checkOldPassword(String username, String password) {
        int var = checkUsername(username);

        if (var == 0) {
            return 0;
        } else {
            PasswordMD5 p5 = new PasswordMD5();
            try {
                password =p5.encode(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            if (var == 1) {
                Employee e = employeeRepository.findByUsername(username);
                if (e.getUsername().equals(username) &&
                        e.getPassword().equals(password)
                ) {
                    return 1;
                } else return 0;

            } else if (var == 2) {
                Customer c = customerRepository.findByUsername(username);
                if (c.getUsername().equals(username) &&
                        c.getPassword().equals(password)
                ) {
                    return 2;
                } else return 0;
            } else {
                Admin a = adminRepository.findByUsername(username);
                if (a.getUsername().equals(password) &&
                        a.getPassword().equals(password)
                ) {
                    return 3;
                } else return 0;
            }
        }
    }

    public void ChangePassword(String email, String password) throws NoSuchAlgorithmException {
        int checkAccount = checkEmail(email);
        password=PasswordMD5.encode(password);
        switch (checkAccount){
            case 0:{
                //K tim thay
                break;
            }
            case 1:{
                //Customer
                Customer c = customerRepository.findByEmail(email);
                c.setPassword(password);
                customerRepository.save(c);
                break;
            }
            case 2:{
                //Employee
                Employee e= employeeRepository.findByEmail(email);
                e.setPassword(password);
                employeeRepository.save(e);
                break;
            }

        }
}

    @SneakyThrows
    public boolean checkLogin(String username, String password) {

        int var = checkUsername(username);

        if (var != 0) {

            if (var == 1) {
                Employee e = employeeRepository.findByUsername(username);
                if (e.getUsername().equals(username) &&
                        e.getPassword().equals(PasswordMD5.encode(password))
                ) {
                    return true;
                } else return false;

            } else if (var == 2) {
                Customer c = customerRepository.findByUsername(username);
                if (c.getUsername().equals(username) &&
                        c.getPassword().equals(PasswordMD5.encode(password))
                ) {
                    return true;
                } else return false;
            } else {
                Admin a = adminRepository.findByUsername(username);
                if (a.getUsername().equals(password) &&
                        a.getPassword().equals(PasswordMD5.encode(password))
                ) {
                    return true;
                } else return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkChangePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        int checkU = checkUsername(username);

        if(checkU != 0) {
            boolean checkNull  = checkNull(oldPassword);
            if(!checkNull) {
                int checkO  =  checkOldPassword(username,oldPassword);
                if (checkO !=0) {
                    PasswordValidate pv = new PasswordValidate();
                    boolean checkV =  pv.checkPasswordValidate(newPassword);
                    if (checkV) {
                        int checkN = checkNewPassword(newPassword,confirmPassword);
                        if(checkN != 0) {
                            return true; // ok
                        } else {
                            return false;// confirmPassword is wrong
                        }
                    } else {
                        return false; // new pass is not valid
                    }
                } else {
                    return false; // old pass is false
                }
            } else {
                return  false; // password is null
            }
        } else {
            return false; // username not exist
        }
    }
}
