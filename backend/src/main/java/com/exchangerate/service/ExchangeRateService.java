package com.exchangerate.service;

import com.exchangerate.model.ExchangeRate;
import com.exchangerate.repository.ExchangeRateRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository repository;
    private final ECBClient ecbClient;

    public ExchangeRateService(ExchangeRateRepository repository, ECBClient ecbClient) {
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

    public double getLatestRate(String from, String to) {
        LocalDate latestDate = repository.findFirstByCurrencyCodeOrderByDateDesc(from)
                .map(ExchangeRate::getDate)
                .orElseThrow(() -> new IllegalArgumentException("No data for currency: " + from));

        double fromRate = repository.findByCurrencyCodeAndDate(from, latestDate)
                .map(ExchangeRate::getRate)
                .orElseThrow(() -> new IllegalArgumentException("No rate found for " + from));
        double toRate = repository.findByCurrencyCodeAndDate(to, latestDate)
                .map(ExchangeRate::getRate)
                .orElseThrow(() -> new IllegalArgumentException("No rate found for " + to));
        return toRate / fromRate;
    }

    public double convertCurrency(double amount, String from, String to) {
        return amount * getLatestRate(from, to);
    }
}