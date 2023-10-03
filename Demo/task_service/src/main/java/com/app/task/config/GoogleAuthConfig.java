package com.app.task.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleAuthConfig {

    @Bean
    public NetHttpTransport getNetHttpTransport() {
        return new NetHttpTransport();
    }

    @Bean
    public GsonFactory getGsonFactory() {
        return new GsonFactory();
    }
}
