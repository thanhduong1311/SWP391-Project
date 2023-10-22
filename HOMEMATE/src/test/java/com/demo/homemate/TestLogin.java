package com.demo.homemate;


import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.EmployeeRepository;
import com.demo.homemate.services.AdminService;
import com.demo.homemate.services.UserService;
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

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestLogin {

    
    @Autowired
    private UserService userService;


    @Test
    void UTCID01() {
        assertEquals(false, userService.checkLogin("",""));
    }

    @Test
    void UTCID02() {
        assertEquals(false, userService.checkLogin("","abc"));
    }

    @Test
    void UTCID03() {
        assertEquals(false, userService.checkLogin("","12345678"));
    }

    @Test
    void UTCID04() {
        assertEquals(false, userService.checkLogin(""," @Duong123"));
    }

    @Test
    void UTCID05() {
        assertEquals(false, userService.checkLogin("","@Duong123"));
    }

    @Test
    void UTCID06() {
        assertEquals(false, userService.checkLogin("huytranhagia",""));
    }

    @Test
    void UTCID07() {
        assertEquals(false, userService.checkLogin("huytranhagia","abc"));
    }

    @Test
    void UTCID08() {
        assertEquals(false, userService.checkLogin("huytranhagia","12345678"));
    }

    @Test
    void UTCID09() {
        assertEquals(false, userService.checkLogin("huytranhagia"," @Duong123"));
    }

    @Test
    void UTCID10() {
        assertEquals(false, userService.checkLogin("huytranhagia","@Duong123"));
    }

    @Test
    void UTCID11() {
        assertEquals(false, userService.checkLogin(" thanhduong01",""));
    }

    @Test
    void UTCID12() {
        assertEquals(false, userService.checkLogin(" thanhduong01","abf"));
    }

    @Test
    void UTCID13() {
        assertEquals(false, userService.checkLogin(" thanhduong01","12345678"));
    }

    @Test
    void UTCID014() {
        assertEquals(false, userService.checkLogin(" thanhduong01"," @Duong123"));
    }

    @Test
    void UTCID015() {
        assertEquals(false, userService.checkLogin(" thanhduong01","@Duong123"));
    }

    @Test
    void UTCID16() {
        assertEquals(false, userService.checkLogin("thanhduong01",""));
    }

    @Test
    void UTCID17() {
        assertEquals(false, userService.checkLogin("thanhduong01","abc"));
    }

    @Test
    void UTCID18() {
        assertEquals(false, userService.checkLogin("thanhduong01","12345678"));
    }

    @Test
    void UTCID19() {
        assertEquals(false, userService.checkLogin("thanhduong01"," @Duong123"));
    }

    @Test
    void UTCID20() {
        assertEquals(true, userService.checkLogin("thanhduong01","@Duong123"));
    }



}
