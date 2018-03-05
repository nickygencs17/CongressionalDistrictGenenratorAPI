package com.sbu.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

//http://localhost:8080/swagger-ui.html

/**
 * Created by Nicholas Genco on 3/1/17.
 * SWAGGER DOCS don't touch this code
 */



//http://localhost:8080/swagger-ui.html
@EnableSwagger2
@Configuration

public class SwaggerConfiguration {

    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.ApiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.sbu.services"))
                .build();
    }

    private ApiInfo ApiInfo() {
        return new ApiInfoBuilder()
                .title("Eagle API")
                .description("Enter the IDs in order to look for the content")
                .version("0.1")
                .build();
    }

    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/basic-error-controller.*"));
    }
}