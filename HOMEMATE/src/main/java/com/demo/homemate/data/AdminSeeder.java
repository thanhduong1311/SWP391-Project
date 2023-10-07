package com.demo.homemate.data;

import com.demo.homemate.entities.Admin;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        if(adminRepository.count() ==0 ) {
            Admin admin = new Admin();
            admin.setUsername("Admin");
            admin.setPassword("123");
            admin.setRole(Role.ADMIN);

            Admin admin1 = new Admin();
            admin.setUsername("Admin1");
            admin.setPassword("1234");
            admin.setRole(Role.ADMIN);

            List<Admin> ls = new ArrayList<>();
            ls.add(admin);
            ls.add(admin1);

            adminRepository.saveAll(ls);

        }
    }
}
