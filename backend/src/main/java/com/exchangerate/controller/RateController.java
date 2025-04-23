package com.exchangerate.controller;

import com.exchangerate.repository.ExchangeRateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RateController {

    private final ExchangeRateRepository repository;

    public RateController(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rates")
    public ResponseEntity<List<String>> getAvailableCurrencies() {
        List<String> currencies = repository.findDistinctCurrencyCodes();
        return ResponseEntity.ok(currencies);
    }
}