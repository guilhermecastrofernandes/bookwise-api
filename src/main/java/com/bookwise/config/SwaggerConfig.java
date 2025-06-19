package com.bookwise.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BookWise API",
                version = "v1",
                description = "Documentação da API para gerenciamento de livros e usuários"
        )
)
public class SwaggerConfig {}

