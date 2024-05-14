package com.Attus.pessoas.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI configOpenApi() {
        return new OpenAPI().info(
                new Info().title("API Gerenciamento de Pessoas Attus")
                        .version("1.0.0")
                        .description("Esta API permite o gerenciamento de informações de pessoas e seus respectivos endereços. " +
                                "Para mais informações, entre em contato com [Dayana Ferreira do Nascimento](https://www.linkedin.com/in/dayanaferreira0/). " +
                                "O código fonte está disponível no [GitHub](https://github.com/Dayanaferrer/Api_Pessoas).")
                        .contact(new Contact().name("Dayana Ferreira do Nascimento").email("dayanaferreira2@gmail.com"))
        );
    }
}