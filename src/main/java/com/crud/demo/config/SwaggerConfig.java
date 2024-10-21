package com.crud.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Test Backend API")
                        .version("1.0.0")
                        .description("API for user authentication , management, and transaction")
                        ).addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
            .components(
                new io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes("Bearer Authentication",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            );

    }
}

// }