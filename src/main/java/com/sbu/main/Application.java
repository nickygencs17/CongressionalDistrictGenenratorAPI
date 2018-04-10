package com.sbu.main;

/**
 * Created by Nicholas Genco on 3/1/17.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = "com.sbu")
@EntityScan("com.sbu")
@EnableJpaRepositories("com.sbu.data")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}