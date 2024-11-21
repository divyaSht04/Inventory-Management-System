package com.individual.divya.inventorymanagementsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerConfiguration(){

        SecurityScheme jwtSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")
                .description("Enter the JWT token in the format: Bearer <token>");

        return new OpenAPI()
               .info(new Info()
                       .title("Inventory Management System")
                       .description("Inventory Management System that facilitates the placement of orders, including the assignment of products to those orders and linking them to appropriate vendors. The system ensures efficient product management and order processing while maintaining strong authentication and authorization mechanisms. ")
               )
                .servers(List.of( new Server().url("http://localhost:8083").description("local Server")))
                .components( new Components()
                        .addSecuritySchemes("BearerAuth", jwtSecurityScheme));
    }
}
