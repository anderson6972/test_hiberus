package com.hiberus.prices;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HiberusApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiberusApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Precios")
                        .version("1.0.0")
                        .description("API para obtener la información del precio final y tarifa aplicable."));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
