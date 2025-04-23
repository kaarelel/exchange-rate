package com.exchangerate.service;

import com.exchangerate.model.ExchangeRate;
import com.exchangerate.repository.ExchangeRateRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository repository;
    private final com.exchangerate.service.ECBClient ecbClient;

    public ExchangeRateService(ExchangeRateRepository repository, com.exchangerate.service.ECBClient ecbClient) {
        this.repository = repository;
        this.ecbClient = ecbClient;
    }

    @PostConstruct
    public void init() throws Exception {
        if (!repository.existsByDate(LocalDate.now().minusDays(1))) {
            loadHistoricalRates();
        }
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void fetchTodayRates() throws Exception {
        loadHistoricalRates();
    }

    public void loadHistoricalRates() throws Exception {
        var rates = ecbClient.fetchRates();
        for (var entry : rates.entrySet()) {
            for (var currency : entry.getValue().entrySet()) {
                ExchangeRate rate = new ExchangeRate();
                rate.setCurrencyCode(currency.getKey());
                rate.setRate(currency.getValue());
                rate.setDate(entry.getKey());
                repository.save(rate);
            }
        }
    }
}