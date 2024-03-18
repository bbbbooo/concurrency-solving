package com.concurrency.concurrencysolving.config;

import java.util.concurrent.Semaphore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemaphoreConfig {

    @Bean
    public Semaphore binarySemaphore() {
        return new Semaphore(1, true);
    }
}
