package com.concurrency.concurrencysolving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ConcurrencySolvingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencySolvingApplication.class, args);
    }

}
