package com.app.task.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(value = 1)
public class DatabaseSeeder implements CommandLineRunner {
    @Override
    @Transactional
    public void run(String... args) throws Exception {
    }
}
