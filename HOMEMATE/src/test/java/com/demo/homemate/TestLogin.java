package com.demo.homemate;


import com.demo.homemate.entities.Customer;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.utils.PasswordMD5;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.BootstrapWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestLogin {

    @Autowired
    private CustomerRepository customerRepository;

    @SneakyThrows
    public boolean checkLogin(String username, String password){
        Customer customer = customerRepository.findByUsername(username);
        if(customer.getUsername().equals(username) && customer.getPassword().equals(PasswordMD5.encode(password))) return  true;
        return  false;
    }
    @Test
    void Test1() {
        assertEquals(true, checkLogin("thanhduong01","@Duong123"));
    }

    @Test
    void Test2() {
        assertEquals(true, checkLogin("thanhduong01","@Duong123"));
    }

}
