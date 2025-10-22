package com.paklog.yard.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI yardOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Yard Management System API")
                .description("Real-time yard and dock management for Paklog WMS/WES")
                .version("1.0.0"));
    }
}
