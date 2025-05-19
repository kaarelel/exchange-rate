package com.exchangerate.scheduler;

import com.exchangerate.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateScheduler.class);
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateScheduler(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Scheduled(cron = "${scheduling.currency-update-cron:0 1 0 * * ?}")
    public void fetchDailyRates() {
        try {
            logger.info("Running scheduled exchange rate fetch...");
            exchangeRateService.loadHistoricalRates();
            logger.info("Exchange rates fetched and saved successfully.");
        } catch (Exception e) {
            logger.error("Failed to fetch exchange rates: {}", e.getMessage(), e);
        }
    }
}
