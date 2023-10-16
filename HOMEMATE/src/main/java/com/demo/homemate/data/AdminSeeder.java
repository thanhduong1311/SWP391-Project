package com.demo.homemate.data;

import com.demo.homemate.entities.Admin;
import com.demo.homemate.enums.Role;
import com.demo.homemate.repositories.AdminRepository;
import com.demo.homemate.utils.PasswordMD5;
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
            admin.setUsername("Admin01");
            admin.setPassword(PasswordMD5.encode("123"));
            admin.setRole(Role.ADMIN);

            Admin admin1 = new Admin();
            admin.setUsername("Admin02");
            admin.setPassword(PasswordMD5.encode("1234"));
            admin.setRole(Role.ADMIN);

            Admin admin2 = new Admin();
            admin.setUsername("Admin03");
            admin.setPassword(PasswordMD5.encode("123@"));
            admin.setRole(Role.ADMIN);

            Admin admin3 = new Admin();
            admin.setUsername("Admin04");
            admin.setPassword(PasswordMD5.encode("1234@"));
            admin.setRole(Role.ADMIN);

            adminRepository.save(admin);
            adminRepository.save(admin1);
            adminRepository.save(admin2);
            adminRepository.save(admin3);

        }
    }
}
