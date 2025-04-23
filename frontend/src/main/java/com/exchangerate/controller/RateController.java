package com.exchangerate.controller;

import com.exchangerate.model.ExchangeRate;
import com.exchangerate.repository.ExchangeRateRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    private final ExchangeRateRepository repository;

    public RateController(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/latest")
    public List<ExchangeRate> getTodayRates() {
        return repository.findAllByDate(LocalDate.now().minusDays(1));
    }

    @GetMapping("/history/{currencyCode}")
    public List<ExchangeRate> getHistory(@PathVariable String currencyCode) {
        return repository.findAllByCurrencyCodeOrderByDateDesc(currencyCode);
    }
}
