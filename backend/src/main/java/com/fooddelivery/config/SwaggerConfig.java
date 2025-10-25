package com.fooddelivery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for auto-generated API docs.
 * Serves UI at /swagger-ui.html and JSON at /v3/api-docs.
 * Why: Review-II requires live hosted Swagger with method, path, auth and schemas visible.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates the OpenAPI definition with JWT bearer scheme.
     * @return configured OpenAPI bean
     */
    @Bean
    public OpenAPI foodDeliveryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Delivery and Live Tracking API")
                        .version("1.1.0")
                        .description("Java Spring Boot backend — JWT auth, orders lifecycle, delivery tracking. Standard response: {success, data, message}.")
                        .contact(new Contact().name("Capstone — Java Batch")))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
