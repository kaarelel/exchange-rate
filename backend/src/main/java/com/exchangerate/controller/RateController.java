package com.exchangerate.controller;

import com.exchangerate.dto.AvailableCurrenciesResponse;
import com.exchangerate.repository.ExchangeRateRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path:/api}")
public class RateController {

    private final ExchangeRateRepository repository;

    public RateController(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @Cacheable("availableCurrencies")
    @GetMapping("${api.rates-path:/rates}")
    public ResponseEntity<Object> getAvailableCurrencies() {
        List<String> currencies = repository.findDistinctCurrencyCodes();
        return ResponseEntity.ok(new AvailableCurrenciesResponse(currencies));
    }
}