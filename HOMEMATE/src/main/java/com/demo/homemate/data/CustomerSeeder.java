package com.demo.homemate.data;

import com.demo.homemate.entities.Customer;
import com.demo.homemate.enums.AccountStatus;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.utils.PasswordMD5;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerSeeder implements CommandLineRunner {

    private final CustomerRepository repository;


    @Override
    public void run(String... args) throws Exception {
        if(repository.count() == 0) {
            Customer user = new Customer();
            user.setCustomerId(1);
            user.setUsername("thanhduong");
            user.setPassword(PasswordMD5.encode("123@"));
            user.setRole(Role.CUSTOMER);
            user.setAccountStatus(AccountStatus.ACTIVE);
            user.setFullName("Nguyen Thanh Duong");
            user.setAvatar("/assets/images/DSC01989.png");
            user.setEmail("thanhduongjnguyen@gmail.com");
            user.setPhone("0813113149");
            user.setGender("male");
            user.setCity("Can Tho");
            user.setDistrict("Ninh Kieu");
            user.setAddress_detail("Tam Vu, Hung Loi");
            user.setBalance(1300);
            user.setTotalSpend(215);
            user.setCreateAt(null);
            user.setUpdateAt(null);




            Customer user2 = new Customer();
            user2.setCustomerId(2);
            user2.setUsername("duongthanh");
            user2.setPassword(PasswordMD5.encode("1234@"));
            user2.setRole(Role.CUSTOMER);
            user2.setAccountStatus(AccountStatus.ACTIVE);
            user2.setFullName("Thanh Duong");
            user2.setAvatar("/assets/images/D1.jpg");
            user2.setEmail("thanhduong@gmail.com");
            user2.setPhone("0813113466");
            user2.setGender("male");
            user2.setCity("Can Tho");
            user2.setDistrict("Ninh Kieu");
            user2.setAddress_detail("Tran Hoang Na, Hung Loi");
            user2.setBalance(1300);
            user2.setTotalSpend(215);
            user2.setCreateAt(null);
            user2.setUpdateAt(null);

            repository.save(user);
            repository.save(user2);



        }
    }
}
