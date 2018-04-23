package com.sbu.controller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost:8080/swagger-ui.html
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.sbu.controller"))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("308API - build #1")
                .description("Gerrymandering API")
                .version("0.1")
                .build();
    }
}