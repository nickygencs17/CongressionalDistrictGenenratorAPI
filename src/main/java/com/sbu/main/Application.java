package com.sbu.main;

/**
 * Created by Nicholas Genco on 3/1/17.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.sbu")
@EntityScan("com.sbu")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}