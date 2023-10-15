package com.demo.homemate.data;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.enums.AccountStatus;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.utils.PasswordMD5;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeSeeder implements CommandLineRunner {

    private final EmployeeRepository repository;


    @Override
    public void run(String... args) throws Exception {
        if(repository.count() == 0) {
            Employee user = new Employee();
            user.setEmployeeId(1);
            user.setUsername("minhhien");
            user.setPassword(PasswordMD5.encode("123@"));
            user.setRole(Role.EMPLOYEE);
            user.setAccountStatus(AccountStatus.ACTIVE);
            user.setFullName("Nguyen Minh Hien");
            user.setAvatar("/assets/images/defaultUser.png");
            user.setEmail("minhhien@gmail.com");
            user.setPhone("0873988349");
            user.setGender("male");
            user.setCity("Can Tho");
            user.setDistrict("Ninh Kieu");
            user.setAddress_detail("Tam Vu, Hung Loi");
            user.setBalance(1300);
            user.setIdCardNumber("372036114");
            user.setWork_place("Can Tho");
            user.setCreateAt(null);
            user.setUpdateAt(null);




            Employee user2 = new Employee();
            user2.setEmployeeId(2);
            user2.setUsername("duongthanh1");
            user2.setPassword(PasswordMD5.encode("1"));
            user2.setRole(Role.EMPLOYEE);
            user2.setAccountStatus(AccountStatus.ACTIVE);
            user2.setFullName("Thanh Duong");
            user2.setAvatar("/assets/images/defaultUser.png");
            user2.setEmail("thanhduong@gmail.com");
            user2.setPhone("0813113466");
            user2.setGender("male");
            user2.setCity("Can Tho");
            user2.setDistrict("Ninh Kieu");
            user2.setAddress_detail("Tran Hoang Na, Hung Loi");
            user2.setBalance(1300);
            user2.setIdCardNumber("372036115");
            user2.setWork_place("Can Tho");
            user2.setCreateAt(null);
            user2.setUpdateAt(null);

            Employee user3 = new Employee();
            user3.setEmployeeId(2);
            user3.setUsername("duongthanh12");
            user3.setPassword(PasswordMD5.encode("12"));
            user3.setRole(Role.EMPLOYEE);
            user3.setAccountStatus(AccountStatus.WAIT_FOR_APPROVE);
            user3.setFullName("Thanh Duong");
            user3.setAvatar("/assets/images/defaultUser.png");
            user3.setEmail("thanhduong33@gmail.com");
            user3.setPhone("0813113468");
            user3.setGender("male");
            user3.setCity("Can Tho");
            user3.setDistrict("Ninh Kieu");
            user3.setAddress_detail("Tran Hoang Na, Hung Loi");
            user3.setBalance(1300);
            user3.setIdCardNumber("372036115");
            user3.setWork_place("Can Tho");
            user3.setCreateAt(null);
            user3.setUpdateAt(null);

            Employee user4 = new Employee();
            user4.setEmployeeId(2);
            user4.setUsername("duongthanh123");
            user4.setPassword(PasswordMD5.encode("123"));
            user4.setRole(Role.EMPLOYEE);
            user4.setAccountStatus(AccountStatus.WAIT_FOR_APPROVE);
            user4.setFullName("Thanh Duong");
            user4.setAvatar("/assets/images/defaultUser.png");
            user4.setEmail("thanhduong123@gmail.com");
            user4.setPhone("0813113462");
            user4.setGender("male");
            user4.setCity("Can Tho");
            user4.setDistrict("Ninh Kieu");
            user4.setAddress_detail("Tran Hoang Na, Hung Loi");
            user4.setBalance(1300);
            user4.setIdCardNumber("372036115");
            user4.setWork_place("Can Tho");
            user4.setCreateAt(null);
            user4.setUpdateAt(null);

            repository.save(user);
            repository.save(user2);
            repository.save(user3);
            repository.save(user4);



        }
    }
}
