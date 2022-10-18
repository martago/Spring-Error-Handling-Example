package com.adams.gaspricecalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    public static final String GAS_INTERFACE_TAG = "Gas Interface Controller";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)) // only classes with @RestController
                .build()
                // if you want to describe your controllers
                .tags(new Tag(GAS_INTERFACE_TAG, "This is your interface to add your gas bills & calculate your new gas price"));
    }
}
