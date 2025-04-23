package com.exchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableScheduling
public class ExchangeRateBackendApplication {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateBackendApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Exchange Rate Backend Application...");
        SpringApplication.run(ExchangeRateBackendApplication.class, args);
    }
}