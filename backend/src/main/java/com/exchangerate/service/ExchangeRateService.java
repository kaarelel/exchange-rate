package com.exchangerate.service;

import com.exchangerate.model.ExchangeRate;
import com.exchangerate.repository.ExchangeRateRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ExchangeRateService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);
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

    @Scheduled(cron = "${scheduling.currency-update-cron:0 1 0 * * ?}")
    public void fetchTodayRates() throws Exception {
        loadHistoricalRates();
    }

    public void loadHistoricalRates() throws Exception {
        var rates = ecbClient.fetchRates();
        int inserted = 0;

        for (var entry : rates.entrySet()) {
            for (var currency : entry.getValue().entrySet()) {
                if (repository.findByCurrencyCodeAndDate(currency.getKey(), entry.getKey()).isEmpty()) {
                    ExchangeRate rate = new ExchangeRate();
                    rate.setCurrencyCode(currency.getKey());
                    rate.setRate(BigDecimal.valueOf(currency.getValue()));
                    rate.setDate(entry.getKey());
                    repository.save(rate);
                    inserted++;
                }
            }
        }

        logger.info("Inserted {} new exchange rate entries.", inserted);
    }

    public BigDecimal getLatestRate(String from, String to) {
        LocalDate latestDate = repository.findFirstByCurrencyCodeOrderByDateDesc(from)
                .map(ExchangeRate::getDate)
                .orElseThrow(() -> new IllegalArgumentException("No data for currency: " + from));

        BigDecimal fromRate = repository.findByCurrencyCodeAndDate(from, latestDate)
                .map(ExchangeRate::getRate)
                .orElseThrow(() -> new IllegalArgumentException("No rate found for " + from));
        BigDecimal toRate = repository.findByCurrencyCodeAndDate(to, latestDate)
                .map(ExchangeRate::getRate)
                .orElseThrow(() -> new IllegalArgumentException("No rate found for " + to));
        return toRate.divide(fromRate, 8, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal convertCurrency(BigDecimal amount, String from, String to) {
        return amount.multiply(getLatestRate(from, to));
    }
}