package com.concurrency.concurrencysolving.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class CouponControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    void semaphoreTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        int numberOfThreads = 100;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.post("/coupon/semaphore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"couponId\": \"1\"}"))
                        .andExpect(status().isOk());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}