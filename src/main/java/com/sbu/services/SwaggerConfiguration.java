package com.sbu.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * This function configures our swagger doc located at http://hostname:8080/swagger-ui.html.
     * @return new swagger docket.
     */

    /**
     * Logger used to log all output throughout entire project.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Docket swaggerDocket() {
        logger.info("Configuring swaggerDoc");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}