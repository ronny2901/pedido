package com.newidea.pedido.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Hello Swagger OpenAPI")
                .version("v1")
                .description("API para estudos. Modulo de Pedido.")
                .license(
                    new License()
                        .name("Apache 2.0")));
    }
}
