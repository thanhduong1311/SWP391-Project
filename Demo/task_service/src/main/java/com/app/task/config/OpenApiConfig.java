package com.app.task.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
/**
 * This class is used for generate swagger document
 */
public class OpenApiConfig {
    public static final String BEARER_SCHEME = "Bearer";
    private static final String API_VERSION = "v1";
    private static final String API_TITLE = "FPT SE Event management API";
    private static final String API_DESCRIPTION = "FPT SE Event Management API";
    @Value("${server.url:http://localhost:8080}")
    private String server;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url(server)
                ))
                .info(
                        new Info()
                                .title(API_TITLE)
                                .description(API_DESCRIPTION)
                                .version(API_VERSION)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .name(BEARER_SCHEME))
                );
    }
}
