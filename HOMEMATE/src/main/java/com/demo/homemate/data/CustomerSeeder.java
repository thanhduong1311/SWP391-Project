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


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerSeeder implements CommandLineRunner {

    private final CustomerRepository repository;


    @Override
    public void run(String... args) throws Exception {
//        if(repository.count() == 0) {
//
//            Customer user = new Customer();
//            user.setCustomerId(1);
//            user.setUsername("thanhduong01");
//            user.setPassword(PasswordMD5.encode("01"));
//            user.setRole(Role.CUSTOMER);
//            user.setAccountStatus(AccountStatus.ACTIVE);
//            user.setFullName("Nguyen Thanh Duong");
//            user.setAvatar("/assets/images/DSC01989.png");
//            user.setEmail("thanhduongjnguyen@gmail.com");
//            user.setPhone("0813113149");
//            user.setGender("male");
//            user.setCity("Can Tho");
//            user.setDistrict("Ninh Kieu");
//            user.setAddress_detail("Tam Vu, Hung Loi");
//            user.setBalance(1300);
//            user.setTotalSpend(215);
//            user.setCreateAt(null);
//            user.setUpdateAt(null);
//
//
//
//
//            Customer user2 = new Customer();
//            user2.setCustomerId(2);
//            user2.setUsername("thanhduong02");
//            user2.setPassword(PasswordMD5.encode("02"));
//            user2.setRole(Role.CUSTOMER);
//            user2.setAccountStatus(AccountStatus.ACTIVE);
//            user2.setFullName("Thanh Duong");
//            user2.setAvatar("/assets/images/D1.jpg");
//            user2.setEmail("thanhduong02@gmail.com");
//            user2.setPhone("0813113466");
//            user2.setGender("male");
//            user2.setCity("Can Tho");
//            user2.setDistrict("Ninh Kieu");
//            user2.setAddress_detail("Tran Hoang Na, Hung Loi");
//            user2.setBalance(1000);
//            user2.setTotalSpend(200);
//            user2.setCreateAt(null);
//            user2.setUpdateAt(null);

//            Customer user3 = new Customer();
//            user3.setCustomerId(2);
//            user3.setUsername("thanhduong03");
//            user3.setPassword(PasswordMD5.encode("03"));
//            user3.setRole(Role.CUSTOMER);
//            user3.setAccountStatus(AccountStatus.ACTIVE);
//            user3.setFullName("Thanh Duong");
//            user3.setAvatar("/assets/images/defaultUser.png");
//            user3.setEmail("thanhduong03@gmail.com");
//            user3.setPhone("0893113466");
//            user3.setGender("male");
//            user3.setCity("Can Tho");
//            user3.setDistrict("Ninh Kieu");
//            user3.setAddress_detail("Tran Hoang Na, Hung Loi");
//            user3.setBalance(1300);
//            user3.setTotalSpend(215);
//            user3.setCreateAt(null);
//            user3.setUpdateAt(null);
//
//            Customer user4 = new Customer();
//            user4.setCustomerId(2);
//            user4.setUsername("thanhduong04");
//            user4.setPassword(PasswordMD5.encode("04"));
//            user4.setRole(Role.CUSTOMER);
//            user4.setAccountStatus(AccountStatus.ACTIVE);
//            user4.setFullName("Thanh Duong");
//            user4.setAvatar("/assets/images/defaultUser.png");
//            user4.setEmail("thanhduong04@gmail.com");
//            user4.setPhone("0813114476");
//            user4.setGender("male");
//            user4.setCity("Can Tho");
//            user4.setDistrict("Ninh Kieu");
//            user4.setAddress_detail("Tran Hoang Na, Hung Loi");
//            user4.setBalance(1300);
//            user4.setTotalSpend(215);
//            user4.setCreateAt(null);
//            user4.setUpdateAt(null);
//
//            Customer user5 = new Customer();
//            user5.setCustomerId(2);
//            user5.setUsername("thanhduong05");
//            user5.setPassword(PasswordMD5.encode("05"));
//            user5.setRole(Role.CUSTOMER);
//            user5.setAccountStatus(AccountStatus.BLOCKED);
//            user5.setFullName("Thanh Duong");
//            user5.setAvatar("/assets/images/defaultUser.png");
//            user5.setEmail("thanhduong05@gmail.com");
//            user5.setPhone("0813813466");
//            user5.setGender("male");
//            user5.setCity("Can Tho");
//            user5.setDistrict("Ninh Kieu");
//            user5.setAddress_detail("Tran Hoang Na, Hung Loi");
//            user5.setBalance(1300);
//            user5.setTotalSpend(215);
//            user5.setCreateAt(null);
//            user5.setUpdateAt(null);
//
//            Customer user6 = new Customer();
//            user6.setCustomerId(2);
//            user6.setUsername("thanhduong06");
//            user6.setPassword(PasswordMD5.encode("06"));
//            user6.setRole(Role.CUSTOMER);
//            user6.setAccountStatus(AccountStatus.ACTIVE);
//            user6.setFullName("Thanh Duong");
//            user6.setAvatar("/assets/images/defaultUser.png");
//            user6.setEmail("thanhduong06@gmail.com");
//            user6.setPhone("0813113966");
//            user6.setGender("male");
//            user6.setCity("Can Tho");
//            user6.setDistrict("Ninh Kieu");
//            user6.setAddress_detail("Tran Hoang Na, Hung Loi");
//            user6.setBalance(1300);
//            user6.setTotalSpend(215);
//            user6.setCreateAt(null);
//            user6.setUpdateAt(null);

//            repository.save(user);
//            repository.save(user2);
////            repository.save(user3);
////            repository.save(user4);
////            repository.save(user4);
////            repository.save(user6);
//
//
//
//        }
    }
}
